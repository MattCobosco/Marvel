package core.models.DTOs;

import java.util.List;

import core.models.Collection;

public class CharactersDto extends Collection<CharacterDto> {
    public List<CharacterDto> getCharacters() {
        return getResults();
    }
}
