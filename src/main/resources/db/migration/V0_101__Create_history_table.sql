create table if not exists "history" (
    id varchar primary key not null,
    user_id varchar references "user"(id),
    itinerary json
);