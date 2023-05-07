package core.api.models.DTOs;

import java.util.List;

import core.api.models.Collection;

public class CharactersDto extends Collection<CharacterDto> {
    public List<CharacterDto> getCharacters() {
        return this.getResults();
    }
}
