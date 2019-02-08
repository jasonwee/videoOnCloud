package play.learn.java.design.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class LambdaInfoApiHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Integer SUCCESS_STATUS_CODE = 200;

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		System.out.println("received: " + input);

		return new ApiGatewayResponse.ApiGatewayResponseBuilder<LambdaInfo>().headers(headers())
				.statusCode(SUCCESS_STATUS_CODE).body(lambdaInfo(context)).build();

	}

	/**
	 * lambdaInfo
	 * 
	 * @param context
	 *            - Lambda context
	 * @return LambdaInfo
	 */
	private LambdaInfo lambdaInfo(Context context) {
		LambdaInfo lambdaInfo = new LambdaInfo();
		lambdaInfo.setAwsRequestId(context.getAwsRequestId());
		lambdaInfo.setFunctionName(context.getFunctionName());
		//lambdaInfo.setFunctionVersion(context.getFunctionVersion());
		lambdaInfo.setLogGroupName(context.getLogGroupName());
		lambdaInfo.setLogStreamName(context.getLogStreamName());
		lambdaInfo.setMemoryLimitInMb(context.getMemoryLimitInMB());

		return lambdaInfo;
	}

	private Map<String, String> headers() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		return headers;
	}

}
