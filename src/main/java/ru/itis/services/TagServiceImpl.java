package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.TagDto;
import ru.itis.models.Tag;
import ru.itis.repositories.TagRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Set<Tag> findOrCreate(Collection<String> tags) {
        Set<Tag> found = new HashSet<>();
        Set<Tag> toBeCreated = new HashSet<>();

        for (String tag : tags) {
            Optional<Tag> byName = tagRepository.findByNameIgnoreCase(tag);

            if (byName.isPresent()) {
                found.add(byName.get());
            } else {
                toBeCreated.add(Tag.builder()
                        .name(tag)
                        .build());
            }
        }

        tagRepository.saveAll(toBeCreated);
        found.addAll(toBeCreated);

        return found;
    }

    @Override
    public Tag findOrCreate(TagDto tags) {
        return null;
    }
}
