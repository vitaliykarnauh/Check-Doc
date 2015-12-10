package com.checkdoc.check;

import com.checkdoc.domain.Document;
import com.checkdoc.domain.Mistake;
import com.checkdoc.domain.MistakeType;
import com.checkdoc.service.MistakeService;
import com.checkdoc.service.MistakeTypeService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Contains logic for finding duplications in documents.
 *
 * @author Oleg Dashkevych.
 */
public class PlagiarismFinder {

	/**
	 * Main document, witch will be compared with other {@link #documents}
	 */
	Document mainDocument;

	/**
	 * List of mistakes (duplicatuions), found in {@link #documents}
	 */
	private List<Mistake> duplicates;

	/**
	 * List of documents to check
	 */
	private List<Document> documents;

	private MistakeTypeFactory typeFactory;

	private MistakeService mistakeService;

	/**
	 * @param documents          to find duplications
	 * @param mistakeService     to add mistakes
	 * @param mistakeTypeService to get mistakeType
	 */
	public PlagiarismFinder(List<Document> documents, MistakeService mistakeService, MistakeTypeService mistakeTypeService) {
		this.documents = documents;
		this.typeFactory = new MistakeTypeFactory(mistakeTypeService);
		this.mistakeService = mistakeService;
	}

	/**
	 * Checks {@link #documents} on duplicates.
	 *
	 * @return List of duplicates in this call of check method
	 */
	public List<Mistake> check() {
		File directory;
		duplicates = new ArrayList<>();
		FileInputStream fis;
		XWPFDocument mainDocxFile;
		XWPFParagraph paragraph;
		List<XWPFParagraph> paraList;
		Document mainDocument;
		if (documents == null) {
			return duplicates;
		}
		if (documents.size() < 2) {
			return duplicates;
		}
		mainDocument = documents.get(0);
		documents.remove(0);
		/** Path of file to check*/
		String savePath = mainDocument.getUrl();
		try {
			directory = new File(savePath);
			fis = new FileInputStream(directory);
			mainDocxFile = new XWPFDocument(fis);
			paraList = mainDocxFile.getParagraphs();
			Iterator<XWPFParagraph> iterator = paraList.iterator();
			int row = 0;
			while (iterator.hasNext()) {
				paragraph = iterator.next();
				if (paragraph == null || paragraph.isEmpty()) {
					continue;
				}
				row++;
				for (Document document : documents) {
					findDuplicates(paragraph, row, document);
				}
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return duplicates;
	}

	/**
	 * Checks plagiarism and adds mistakes to DB
	 *
	 * @param paragraph of {@link #mainDocument}
	 * @param row       in the {@link #mainDocument} of current paragraph
	 * @param document  from {@link #documents} to compare with {@link #mainDocument}
	 */
	private void findDuplicates(XWPFParagraph paragraph, Integer row, Document document) {
		File file;
		FileInputStream fis;
		XWPFDocument docToCheck;
		XWPFParagraph paragraphToCheck;
		List<XWPFParagraph> paraList;
		Iterator<XWPFParagraph> iterator;
		/** Path of file to check*/
		String savePath = document.getUrl();
		String paragraphText = paragraph.getParagraphText();
		try {
			file = new File(savePath);
			fis = new FileInputStream(file);
			docToCheck = new XWPFDocument(fis);
			paraList = docToCheck.getParagraphs();
			iterator = paraList.iterator();
			while (iterator.hasNext()) {
				paragraphToCheck = iterator.next();
				if (paragraphToCheck == null || paragraphToCheck.isEmpty()) {
					continue;
				}
				if (paragraphText.equals(paragraphToCheck.getParagraphText())) {
					MistakeType type = typeFactory.getMistakeType(MistakeTypeEnum.PLAGIARISM);
					if(paragraphText.length() > 100){
						paragraphText = paragraphText.substring(0, 100);
					}
					Mistake mistake = new Mistake(paragraphText, row, document, type);
					//if(!duplicates.contains(mistake)) {
						mistakeService.add(mistake);
						duplicates.add(mistake);
					//}
				}
			}
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

