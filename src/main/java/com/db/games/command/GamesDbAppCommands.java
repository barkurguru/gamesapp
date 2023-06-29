package com.db.games.command;

import com.db.games.domain.*;
import com.db.games.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.data.domain.*;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@ShellComponent
public class GamesDbAppCommands {

    @Autowired
    private GameService gameService;

	@ShellMethod(value = "Get all game games", key = {"all-games", "ag"})
    private String getAllGames() {
    	String data=gameService.getAllGames().stream().map(Game::toString).collect(Collectors.joining("\n"));
        return data;
    }

}
