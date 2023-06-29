package com.db.games.service;

import com.db.games.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import java.util.List;

@Service
public interface PlatformService {

    List<Platform> getAllPlatforms();
	
	
}
