package carrental.domain;

public class PassportInfo {
	private Integer userId;
	private String series;
	private Integer number;
	private String additionalInfo;
	private Long issuedMillis;

	public PassportInfo() {
		super();
	}

	public PassportInfo(Integer userId, String series, Integer number, String additionalInfo, Long issuedMillis) {
		super();
		this.userId = userId;
		this.series = series;
		this.number = number;
		this.additionalInfo = additionalInfo;
		this.issuedMillis = issuedMillis;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Long getIssuedMillis() {
		return issuedMillis;
	}

	public void setIssuedMillis(Long issuedMillis) {
		this.issuedMillis = issuedMillis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalInfo == null) ? 0 : additionalInfo.hashCode());
		result = prime * result + ((issuedMillis == null) ? 0 : issuedMillis.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		PassportInfo other = (PassportInfo) obj;
		if (additionalInfo == null) {
			if (other.additionalInfo != null)
				return false;
		} else if (!additionalInfo.equals(other.additionalInfo))
			return false;
		if (issuedMillis == null) {
			if (other.issuedMillis != null)
				return false;
		} else if (!issuedMillis.equals(other.issuedMillis))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PassportInfo [userId=").append(userId).append(", series=").append(series).append(", number=").append(number)
				.append(", additionalInfo=").append(additionalInfo).append(", issuedMillis=").append(issuedMillis).append("]");
		return builder.toString();
	}

}
