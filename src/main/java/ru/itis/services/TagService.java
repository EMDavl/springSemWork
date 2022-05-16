package ru.itis.services;

import ru.itis.dto.TagDto;
import ru.itis.models.Tag;

import java.util.Collection;
import java.util.Set;

public interface TagService {

    Set<Tag> findOrCreate(Collection<String> tags);

    Tag findOrCreate(TagDto tags);

}
