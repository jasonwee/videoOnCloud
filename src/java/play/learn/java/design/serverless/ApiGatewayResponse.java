package play.learn.java.design.serverless;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

public class ApiGatewayResponse<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1181159426782844892L;

	private final Integer statusCode;
	private final String body;
	private final Map<String, String> headers;
	private final Boolean isBase64Encoded;

	/**
	 * api gateway response
	 *
	 * @param statusCode
	 *            - http status code
	 * @param body
	 *            - response body
	 * @param headers
	 *            - response headers
	 * @param isBase64Encoded
	 *            - base64Encoded flag
	 */
	public ApiGatewayResponse(Integer statusCode, String body, Map<String, String> headers, Boolean isBase64Encoded) {
		this.statusCode = statusCode;
		this.body = body;
		this.headers = headers;
		this.isBase64Encoded = isBase64Encoded;
	}

	/**
	 * http status code
	 *
	 * @return statusCode - http status code
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * response body
	 *
	 * @return string body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * response headers
	 *
	 * @return response headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * base64Encoded flag, API Gateway expects the property to be called
	 * "isBase64Encoded"
	 *
	 * @return base64Encoded flag
	 */
	public Boolean isBase64Encoded() {
		return isBase64Encoded;
	}

	/**
	 * ApiGatewayResponse Builder class
	 * 
	 * @param <T>
	 *            Serializable object
	 */
	public static class ApiGatewayResponseBuilder<T extends Serializable> {
		private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
		private Integer statusCode;
		private T body;
		private Map<String, String> headers;
		private Boolean isBase64Encoded;

		/**
		 * http status code
		 * 
		 * @param statusCode
		 *            - http status code
		 * @return ApiGatewayResponseBuilder
		 */
		public ApiGatewayResponseBuilder statusCode(Integer statusCode) {
			this.statusCode = statusCode;
			return this;
		}

		/**
		 * Serializable body
		 * 
		 * @param body
		 *            - Serializable object
		 * @return ApiGatewayResponseBuilder
		 */
		public ApiGatewayResponseBuilder body(T body) {
			this.body = body;
			return this;
		}

		/**
		 * response headers
		 * 
		 * @param headers
		 *            - response headers
		 * @return ApiGatewayResponseBuilder
		 */
		public ApiGatewayResponseBuilder headers(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}

		/**
		 * base64Encoded glag
		 * 
		 * @param isBase64Encoded
		 *            - base64Encoded flag
		 * @return ApiGatewayResponseBuilder
		 */
		public ApiGatewayResponseBuilder base64Encoded(Boolean isBase64Encoded) {
			this.isBase64Encoded = isBase64Encoded;
			return this;
		}

		/**
		 * build ApiGatewayResponse
		 *
		 * @return ApiGatewayResponse
		 */
		public ApiGatewayResponse build() {
			String strBody = null;
			if (this.body != null) {
				try {
					strBody = OBJECT_MAPPER.writeValueAsString(body);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}

			return new ApiGatewayResponse(this.statusCode, strBody, this.headers, this.isBase64Encoded);
		}
	}

}
