package com.backend.TalkNestResourceServer.domain.dtos.auths;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectResponse {

    private boolean isValid;

}
