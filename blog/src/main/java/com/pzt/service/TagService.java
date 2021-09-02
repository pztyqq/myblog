package com.pzt.service;

import com.pzt.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);
}
