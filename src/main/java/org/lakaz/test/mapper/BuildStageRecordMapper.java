package org.lakaz.test.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lakaz.test.dto.BuildStageRecordDTO;
import org.lakaz.test.dto.KafkaMessageDTO;
import org.lakaz.test.model.BuildStageRecord;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class BuildStageRecordMapper {

    ObjectMapper mapper = new ObjectMapper();

    public BuildStageRecord map(String jsonString) throws BuildStageRecordMapperException {

        try {
            KafkaMessageDTO kafkaMessageDTO =  mapper.readValue(jsonString, KafkaMessageDTO.class);
            BuildStageRecord buildStageRecord = new BuildStageRecord();
            buildStageRecord.setDuration(kafkaMessageDTO.getOperationTook());
            buildStageRecord.setBuildStage(kafkaMessageDTO.getMdc().getProcessStageName());
            buildStageRecord.setBuildId(kafkaMessageDTO.getMdc().getProcessContext());
            return buildStageRecord;

        } catch(IOException e) {
            throw new BuildStageRecordMapperException(e);
        }
    }

    public String toJsonString(BuildStageRecordDTO buildStageRecord) throws BuildStageRecordMapperException {

        try {
            return mapper.writeValueAsString(buildStageRecord);
        } catch(IOException e) {
            throw new BuildStageRecordMapperException(e);
        }
    }

}
