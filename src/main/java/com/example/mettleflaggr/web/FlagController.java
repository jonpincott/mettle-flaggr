package com.example.mettleflaggr.web;

import com.example.mettleflaggr.dto.FlagDto;
import com.example.mettleflaggr.service.FlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/flag")
@RequiredArgsConstructor
public class FlagController {

    private final FlagService flagService;

    @PostMapping
    @ResponseStatus(CREATED)
    public FlagDto createFlag(@Valid @RequestBody FlagDto flagDto) {
        return flagService.createFlag(flagDto);
    }

    @GetMapping
    public List<FlagDto> getFlags() {
        return flagService.findAllFlags();
    }

}
