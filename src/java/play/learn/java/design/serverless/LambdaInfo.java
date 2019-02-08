package play.learn.java.design.serverless;

import java.io.Serializable;

public class LambdaInfo implements Serializable {
	private static final long serialVersionUID = 3936130599040848923L;

	private String awsRequestId;
	private String logGroupName;
	private String logStreamName;
	private String functionName;
	private String functionVersion;
	private Integer memoryLimitInMb;

	public String getAwsRequestId() {
		return awsRequestId;
	}

	public void setAwsRequestId(String awsRequestId) {
		this.awsRequestId = awsRequestId;
	}

	public String getLogGroupName() {
		return logGroupName;
	}

	public void setLogGroupName(String logGroupName) {
		this.logGroupName = logGroupName;
	}

	public String getLogStreamName() {
		return logStreamName;
	}

	public void setLogStreamName(String logStreamName) {
		this.logStreamName = logStreamName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionVersion() {
		return functionVersion;
	}

	public void setFunctionVersion(String functionVersion) {
		this.functionVersion = functionVersion;
	}

	public Integer getMemoryLimitInMb() {
		return memoryLimitInMb;
	}

	public void setMemoryLimitInMb(Integer memoryLimitInMb) {
		this.memoryLimitInMb = memoryLimitInMb;
	}

	@Override
	public String toString() {
		return "LambdaInfo{" + "awsRequestId='" + awsRequestId + '\'' + ", logGroupName='" + logGroupName + '\''
				+ ", logStreamName='" + logStreamName + '\'' + ", functionName='" + functionName + '\''
				+ ", functionVersion='" + functionVersion + '\'' + ", memoryLimitInMb=" + memoryLimitInMb + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		LambdaInfo that = (LambdaInfo) o;

		if (awsRequestId != null ? !awsRequestId.equals(that.awsRequestId) : that.awsRequestId != null) {
			return false;
		}
		if (logGroupName != null ? !logGroupName.equals(that.logGroupName) : that.logGroupName != null) {
			return false;
		}
		if (logStreamName != null ? !logStreamName.equals(that.logStreamName) : that.logStreamName != null) {
			return false;
		}
		if (functionName != null ? !functionName.equals(that.functionName) : that.functionName != null) {
			return false;
		}
		if (functionVersion != null ? !functionVersion.equals(that.functionVersion) : that.functionVersion != null) {
			return false;
		}
		return memoryLimitInMb != null ? memoryLimitInMb.equals(that.memoryLimitInMb) : that.memoryLimitInMb == null;
	}

	@Override
	public int hashCode() {
		int result = awsRequestId != null ? awsRequestId.hashCode() : 0;
		result = 31 * result + (logGroupName != null ? logGroupName.hashCode() : 0);
		result = 31 * result + (logStreamName != null ? logStreamName.hashCode() : 0);
		result = 31 * result + (functionName != null ? functionName.hashCode() : 0);
		result = 31 * result + (functionVersion != null ? functionVersion.hashCode() : 0);
		result = 31 * result + (memoryLimitInMb != null ? memoryLimitInMb.hashCode() : 0);
		return result;
	}
}
