package com.FreeBirds.FreeBirds.config;

import com.FreeBirds.FreeBirds.dtos.*;
import com.FreeBirds.FreeBirds.entities.*;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Ignore null values on update
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        // ✅ Custom mappings for collections
        TypeMap<User, UserDTO> userToDto = mapper.createTypeMap(User.class, UserDTO.class);

        userToDto.addMappings(m -> {
            m.map(User::getSkills, UserDTO::setSkills);
            m.map(User::getProjects, UserDTO::setProjects);
            m.map(User::getEducation, UserDTO::setEducation);
        });

        // ✅ Individual mappings for child entities
        mapper.createTypeMap(Skill.class, SkillDTO.class);
        mapper.createTypeMap(Project.class, ProjectDTO.class);
        mapper.createTypeMap(Education.class, EducationDTO.class);

        return mapper;
    }
}
