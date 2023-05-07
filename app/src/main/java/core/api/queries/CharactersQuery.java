package core.api.queries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.utils.DateUtil;

public class CharactersQuery {
    private static final String QUERY_NAME = "name";
    private static final String QUERY_NAME_START_WITH = "nameStartsWith";
    private static final String QUERY_MODIFIED_SINCE = "modifiedSince";
    private static final String QUERY_COMICS = "comics";
    private static final String QUERY_SERIES = "series";
    private static final String QUERY_EVENTS = "events";
    private static final String QUERY_STORIES = "stories";
    private static final String QUERY_ORDER_BY = "orderBy";
    private static final String QUERY_LIMIT = "limit";
    private static final String QUERY_OFFSET = "offset";

    private final String name;
    private final String nameStartWith;
    private final String modifiedSince;
    private final String comics;
    private final String series;
    private final String events;
    private final String stories;
    private final String orderBy;
    private final int limit;
    private final int offset;

    private CharactersQuery(final String name, final String nameStartWith, final String modifiedSince, final String comics,
                            final String series, final String events, final String stories, final String orderBy, final int limit, final int offset) {
        this.name = name;
        this.nameStartWith = nameStartWith;
        this.modifiedSince = modifiedSince;
        this.comics = comics;
        this.series = series;
        this.events = events;
        this.stories = stories;
        this.orderBy = orderBy;
        this.limit = limit;
        this.offset = offset;
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> returnValues = new HashMap<>();

        if (null != name) {
            returnValues.put(QUERY_NAME, this.name);
        }

        if (null != nameStartWith) {
            returnValues.put(QUERY_NAME_START_WITH, this.nameStartWith);
        }

        if (null != modifiedSince) {
            returnValues.put(QUERY_MODIFIED_SINCE, this.modifiedSince);
        }

        if (null != comics) {
            returnValues.put(QUERY_COMICS, this.comics);
        }

        if (null != series) {
            returnValues.put(QUERY_SERIES, this.series);
        }

        if (null != events) {
            returnValues.put(QUERY_EVENTS, this.events);
        }

        if (null != stories) {
            returnValues.put(QUERY_STORIES, this.stories);
        }

        if (null != orderBy) {
            returnValues.put(QUERY_ORDER_BY, this.orderBy);
        }

        if (0 < limit) {
            returnValues.put(QUERY_LIMIT, this.limit);
        }

        if (0 < offset) {
            returnValues.put(QUERY_OFFSET, this.offset);
        }

        return returnValues;
    }


    public static class Builder {
        public static final int MAX_SIZE = 100;

        public static final String LIST_SEPARATOR = ",";
        private String name;
        private String nameStartWith;
        private Date modifiedSince;
        private final List<Integer> comics = new ArrayList<>();
        private final List<Integer> series = new ArrayList<>();
        private final List<Integer> events = new ArrayList<>();
        private final List<Integer> stories = new ArrayList<>();
        private String orderBy;
        private boolean orderByAscendant;
        private int limit;
        private int offset;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withNameStartWith(final String nameStartWith) {
            this.nameStartWith = nameStartWith;
            return this;
        }

        public Builder withModifiedSince(final Date modifiedSince) {
            this.modifiedSince = modifiedSince;
            return this;
        }

        public Builder addComic(final int comic) {
            this.comics.add(comic);
            return this;
        }

        public Builder addComics(final List<Integer> comics) {
            this.checkNull(comics);
            this.comics.addAll(comics);
            return this;
        }

        public Builder addSerie(final int serie) {
            this.series.add(serie);
            return this;
        }

        public Builder addSeries(final List<Integer> series) {
            this.checkNull(series);
            this.series.addAll(series);
            return this;
        }

        public Builder addEvent(final int event) {
            this.events.add(event);
            return this;
        }

        public Builder addEvents(final List<Integer> events) {
            this.checkNull(events);
            this.events.addAll(events);
            return this;
        }

        public Builder addStory(final int story) {
            this.stories.add(story);
            return this;
        }

        public Builder addStory(final List<Integer> stories) {
            this.checkNull(stories);
            this.stories.addAll(stories);
            return this;
        }

        public Builder withOrderBy(final String orderBy, final boolean ascendant) {
            this.orderBy = orderBy;
            orderByAscendant = ascendant;
            return this;
        }

        public Builder withOrderBy(final String orderBy) {
            this.orderBy = orderBy;
            orderByAscendant = true;
            return this;
        }

        public Builder withLimit(final int limit) {
            this.checkLimit(limit);
            this.limit = limit;
            return this;
        }

        public Builder withOffset(final int offset) {
            if (0 > offset) {
                throw new IllegalArgumentException("offset must be bigger or equals than zero");
            }

            this.offset = offset;
            return this;
        }

        public CharactersQuery build() {
            final String plainModifedSince = this.convertDate(this.modifiedSince);
            final String plainComics = this.convertToList(this.comics);
            final String plainEvents = this.convertToList(this.events);
            final String plainSeries = this.convertToList(this.series);
            final String plainStories = this.convertToList(this.stories);
            final String plainOrderBy = this.convertOrderBy(this.orderBy, this.orderByAscendant);

            return new CharactersQuery(this.name, this.nameStartWith, plainModifedSince, plainComics, plainSeries,
                    plainEvents, plainStories, plainOrderBy, this.limit, this.offset);
        }

        private void checkLimit(final int limit) {
            if (0 >= limit) {
                throw new IllegalArgumentException("limit must be bigger than zero");
            }

            if (MAX_SIZE < limit) {
                throw new IllegalArgumentException("limit must be smaller than 100");
            }
        }

        private void checkNull(final List<Integer> list) {
            if (null == list) {
                throw new IllegalArgumentException("the collection can not be null");
            }
        }

        private String convertDate(final Date date) {
            if (null == date) {
                return null;
            }
            return DateUtil.parseDate(date);
        }

        private String convertOrderBy(final String orderBy, final boolean ascendant) {
            if (null == orderBy) {
                return null;
            }
            return (ascendant) ? orderBy : "-" + orderBy;
        }

        private String convertToList(final List<Integer> list) {
            String plainList = "";
            for (int i = 0; i < list.size(); i++) {
                plainList += Integer.toString(list.get(i));
                if (i < list.size() - 1) {
                    plainList += Builder.LIST_SEPARATOR;
                }
            }
            return (plainList.isEmpty()) ? null : plainList;
        }
    }
}
