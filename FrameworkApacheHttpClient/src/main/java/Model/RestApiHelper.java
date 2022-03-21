package Model;

import java.util.Map;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestApiHelper {

	public static RestResponse performGetRequest(String Url, Map<String, String> headers) {
		HttpGet get = new HttpGet(Url);
		RestResponse rest = null;
		CloseableHttpResponse response = null;
		if (headers != null) {
			for (String str : headers.keySet()) {
				get.addHeader(str, headers.get(str));
			}
		}
		try (CloseableHttpClient client = HttpClientBuilder.create().build();) {
			response = client.execute(get);
			ResponseHandler<String> body = new BasicResponseHandler();
			rest = new RestResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));

		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}

			throw new RuntimeException(e.getMessage(), e);
		}
		return rest;
	}

	public static RestResponse performPostRequest(String Url, String content, Map<String, String> headers) {
		HttpPost post = new HttpPost(Url);
		RestResponse rest = null;
		CloseableHttpResponse response = null;

		if (headers != null) {
			for (String str : headers.keySet()) {
				post.addHeader(str, headers.get(str));
			}
		}
		post.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			response = client.execute(post);
			ResponseHandler<String> handler = new BasicResponseHandler();
			rest = new RestResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response));

		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}

			throw new RuntimeException(e.getMessage(), e);
		}
		return rest;

	}

	public static RestResponse performPutRequest(String Url, String content, Map<String, String> headers) {
		HttpPost put = new HttpPost(Url);
		RestResponse rest = null;
		CloseableHttpResponse response = null;

		if (headers != null) {
			for (String str : headers.keySet()) {
				put.addHeader(str, headers.get(str));
			}
		}
		put.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			response = client.execute(put);
			ResponseHandler<String> handler = new BasicResponseHandler();
			rest = new RestResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response));

		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}

			throw new RuntimeException(e.getMessage(), e);
		}
		return rest;

	}

	public static RestResponse performDeleteRequest(String Url, Map<String, String> headers) {
		HttpDelete get = new HttpDelete(Url);
		RestResponse rest = null;
		CloseableHttpResponse response = null;
		if (headers != null) {
			for (String str : headers.keySet()) {
				get.addHeader(str, headers.get(str));
			}
		}
		try (CloseableHttpClient client = HttpClientBuilder.create().build();) {
			response = client.execute(get);
			ResponseHandler<String> body = new BasicResponseHandler();
			rest = new RestResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));

		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}

			throw new RuntimeException(e.getMessage(), e);
		}
		return rest;
	}
}
