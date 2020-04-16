package br.eti.arthurgregorio.library.infrastructure.spring.init;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 01/10/2019
 */
@Slf4j
@Service
@Profile("alpha")
public class ApplicationInitializer {

    private List<InitialTask> tasks;

    /**
     *
     *
     * @param tasks
     */
    public ApplicationInitializer(List<InitialTask> tasks) {
        this.tasks = tasks;
    }

    /**
     *
     */
    @PostConstruct
    public void orderTasks() {
        this.tasks.sort(AnnotationAwareOrderComparator.INSTANCE);
        log.info("Ordering tasks");
    }

    /**
     * {@inheritDoc}
     *
     * @param event
     */
    @EventListener
    public void onApplicationStart(ContextRefreshedEvent event) {
        log.info("Running application initializer tasks");
        this.tasks.forEach(InitialTask::perform);
        log.info("{} tasks performed", this.tasks.size());
    }
}
