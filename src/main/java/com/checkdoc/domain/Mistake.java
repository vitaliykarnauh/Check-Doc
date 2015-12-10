package com.checkdoc.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "MISTAKE")
@Table(name = "MISTAKE")
public class Mistake {

	@Id
	@GeneratedValue(generator = "identity")
	@GenericGenerator(name = "identity", strategy = "identity")
	@Column(name = "MISTAKE_ID", unique = true, nullable = false)
	private Long id;

	@Lob
	@Column(name = "TEXT")
	private String text;

	@Column(name = "ROW")
	private Integer row;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DOCUMENT_ID")
	private Document document;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MISTAKE_TYPE_ID")
	private MistakeType mistakeType;

	public Mistake(String text, Document document, MistakeType mistakeType) {
		this.text = text;
		this.document = document;
		this.mistakeType = mistakeType;
	}

	public Mistake(String text, Integer row, Document document, MistakeType mistakeType) {
		this(text, document, mistakeType);
		this.row = row;

	}

	public Mistake() {
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public MistakeType getMistakeType() {
		return mistakeType;
	}

	public void setMistakeType(MistakeType mistakeType) {
		this.mistakeType = mistakeType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Mistake)) return false;

		Mistake mistake = (Mistake) o;

//		if (!getId().equals(mistake.getId())) return false;
		if (!getText().equals(mistake.getText())) return false;
		if (getRow() != null ? !getRow().equals(mistake.getRow()) : mistake.getRow() != null) return false;
		if (!getDocument().equals(mistake.getDocument())) return false;
		return getMistakeType().equals(mistake.getMistakeType());

	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getText().hashCode();
		result = 31 * result + (getRow() != null ? getRow().hashCode() : 0);
		result = 31 * result + getDocument().hashCode();
//		result = 31 * result + getMistakeType().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Mistake{" +
				"text='" + text + '\'' +
				", document=" + document +
				", mistakeType=" + mistakeType +
				'}';
	}
}
