package mdn.assessment.logsearch.api.logs.controller;

import mdn.assessment.logsearch.api.logs.model.Log;
import mdn.assessment.logsearch.api.logs.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * Returns Server logs
     *
     * @param params Query Parameters.
     *
     * @return List of Logs.
     */
    @GetMapping("/")
    public ResponseEntity<List<Log>> fetchLogs(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(logService.fetchLogs(params));
    }
}
