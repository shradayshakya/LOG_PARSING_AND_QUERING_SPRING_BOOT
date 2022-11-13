package mdn.assessment.logsearch;

import mdn.assessment.logsearch.api.logs.model.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class EntryPoint {

	/**
	 * Entry point of the Application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EntryPoint.class, args);
	}
}
