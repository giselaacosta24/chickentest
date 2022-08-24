package com.accenture.chickentest.springbatch;



import com.accenture.chickentest.service.TransformationService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


@Component
public class JobTransformation implements Tasklet {


    private TransformationService transformationService;


    public JobTransformation(TransformationService transformationService) {
        this.transformationService = transformationService;
    }
    @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        transformationService.updateDays();
        transformationService.eggToChicken(8L);
        transformationService.putAnEgg(8L);
        transformationService.chickenToDead(8L);







        return RepeatStatus.FINISHED;

    }
}
