package com.cygnus.jett.mapper;

import com.cygnus.jett.controller.response.InstructorsAvailableResponse;
import com.cygnus.jett.integration.resources.InstructorsJettBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface InstructorsAvailableResponseMapper {

    public static final InstructorsAvailableResponseMapper INSTANCE = Mappers.getMapper(InstructorsAvailableResponseMapper.class);

    default List<InstructorsAvailableResponse> toInstructorsAvailableResponse(final InstructorsJettBookingResponse request){

        List<InstructorsAvailableResponse> listInstructors = new ArrayList<InstructorsAvailableResponse>();

        int count = 0;

        request.getData().forEach(inst ->{
            InstructorsAvailableResponse response = new InstructorsAvailableResponse();

            response.setRank(count+1);
            response.setId(inst.getId());
            response.setName(inst.getName());
            response.setPhone(inst.getPhone());
            response.setEmail(inst.getEmail());

            listInstructors.add(response);
        });

        return listInstructors;
    }
}
