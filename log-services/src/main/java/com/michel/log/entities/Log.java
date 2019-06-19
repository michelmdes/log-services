package com.michel.log.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michel.log.utils.Util;

/**
 * @author Michel Mendes	17/06/2019
 * Entidade Log
 */
@Table(name="log", schema="log_manager")
@Entity
public class Log implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String fileName;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "log", cascade = CascadeType.REMOVE)
	@JsonIgnore
    private List<LogData> logDataList;

	
	public Log() {
		super();
	}

	public Log(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public List<LogData> getLogDataList() {
		return logDataList;
	}

	public void setLogDataList(List<LogData> logDataList) {
		this.logDataList = logDataList;
	}
	
	public String getDataFmt() {
		return Util.parseDateToString(data, Util.DATE_TIME_PATTERN);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
