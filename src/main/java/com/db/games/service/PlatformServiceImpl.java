package com.db.games.service;

import com.db.games.domain.Platform;
import com.db.games.dto.*;
import com.db.games.exception.GamesAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.*;
import com.db.games.repsitory.*;
import com.db.games.domain.*;

@Service
public class PlatformServiceImpl implements PlatformService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${platforms.list.url}")
    private String platformListApiUrl;

    @Value("${api.key}")
    private String apiKey;
	
	@Autowired
	PlatformRepository platformRepository;

    @Override
    public List<Platform> getAllPlatforms() {
        LOGGER.debug("Start of getAllPlatforms method ==> ");
		System.out.println("Start of getAllPlatforms method ==> ");   
		String url = buildUrl(platformListApiUrl);
		LOGGER.debug("Start of Rest Template Exhange withparams {}  ==> ", url);
		return getAllPageData(url);
        
    }
	
	private List<Platform>  getAllPageData(String url) {
		List<Platform> data=new ArrayList<Platform>();
        HttpHeaders headers = buildRequest();
        HttpEntity<String> request = new HttpEntity<>(headers);
		do{			
			ResponseEntity<PlatformDto> response = restTemplate.exchange(url, HttpMethod.GET, request,
						new ParameterizedTypeReference<PlatformDto>() {
						});
			PlatformDto resp = response.getBody();	
			if (CollectionUtils.isEmpty(resp.getResults())) {
					LOGGER.debug("Empty RESPONSE Received from API", resp);
					System.out.println("Empty RESPONSE Received from API"+ resp);
					return resp.getResults();
				}
			System.out.println("End of Rest Template Exhange and response received {}  with size ==> "+ resp.getResults().size()+",resp: "+ resp);
			LOGGER.debug("End of Rest Template Exhange and response received {}  with size ==> ", resp, resp.getResults().size());
			data.addAll(response.getBody().getResults());
			url=response.getBody().getNext();
		} while (url!=null);
		if(data!=null)
			platformRepository.saveAll(data);
		return data;
	}

    private HttpHeaders buildRequest() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    private String buildUrl(String apiUrl) {
		System.out.println(apiUrl + "?key=" + apiKey);
        return apiUrl + "?key=" + apiKey;
    }
	
	
}
