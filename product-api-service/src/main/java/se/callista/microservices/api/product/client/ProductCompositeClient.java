package se.callista.microservices.api.product.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productcomposite")
public interface ProductCompositeClient {

	@RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
	ResponseEntity<String> getProduct(@PathVariable("productId") int productId);

}
