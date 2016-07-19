package se.callista.microservices.api.product.oauth2.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor {

	static final String AUTHORITIES = "authorities";

	@Override
	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
		String authorities = "ROLE_USER";
		if (map.containsKey(AUTHORITIES)) {
			authorities = asAuthorities(map.get(AUTHORITIES));
		}
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	@SuppressWarnings("unchecked")
	private String asAuthorities(Object object) {
		if (object instanceof Collection) {
			return (String) ((Collection) object).stream().map(o -> {
				if (o instanceof Map) {
					return ((Map) o).values().stream().collect(Collectors.joining(","));
				}
				return o.toString();
			}).collect(Collectors.joining(","));

		}
		if (ObjectUtils.isArray(object)) {
			return StringUtils.arrayToCommaDelimitedString((Object[]) object);
		}
		return object.toString();
	}

}