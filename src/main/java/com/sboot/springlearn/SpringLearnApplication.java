package com.sboot.springlearn;

import com.sboot.springlearn.business.service.ReservationService;
import com.sboot.springlearn.data.entity.Guest;
import com.sboot.springlearn.data.entity.Reservation;
import com.sboot.springlearn.data.entity.Room;
import com.sboot.springlearn.data.repository.GuestRepository;
import com.sboot.springlearn.data.repository.ReservationRepository;
import com.sboot.springlearn.data.repository.RoomRepository;
import com.sboot.springlearn.web.GreetWebController;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import javassist.tools.reflect.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringLearnApplication {

	public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ResourceException, ScriptException, NoSuchMethodException, InvocationTargetException {
		SpringApplication.run(SpringLearnApplication.class, args);
		try(WatchService service = FileSystems.getDefault().newWatchService()){
			Map<WatchKey, Path> keyMap = new HashMap<>();
			Path path = Paths.get("C:/Users/Arya/Desktop/Groovy");
			keyMap.put(path.register(service,
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.OVERFLOW),
					path);

			WatchKey watchKey;
			do{
				watchKey = service.take();
				Path eventDir = keyMap.get(keyMap.get(watchKey));
				ReservationService reservationService = null;
				GreetWebController gwc =new GreetWebController(reservationService);

				for (WatchEvent<?> event: watchKey.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					Path eventPath =(Path) event.context();
					if(StandardWatchEventKinds.ENTRY_MODIFY==kind ||  (eventPath).equals("file1.groovy")  )
					{
						gwc.CallGroovy();
					}
					System.out.println("Type of event: "+kind+ " File Modified: "+eventPath);

				}
			}
			while (watchKey.reset());
		}catch(Exception e){

		}
	}

}





