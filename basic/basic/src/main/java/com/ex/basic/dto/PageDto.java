package com.ex.basic.dto;

import com.ex.basic.entity.MemberEntity;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageDto {
    private int totalPages;
    private int currentPage;
    private List<MemberEntity> list;
}
