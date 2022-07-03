package com.msocial.movie_service.constant;

public interface Endpoints {

//    API_ENDPOINTS

    String API = "api/v1";

    String API_MOVIES = API + "/movies";

    String API_MOVIES_PAGE = API_MOVIES + "/{page}";

    String API_FAVORITES = API + "/favorites";

    String API_RECOMMEND = API + "/recommend";

    String API_FAVORITES_ID = API_FAVORITES + "/{id}";

    String API_USER = API + "/user";

    String API_USER_REGISTRATION = API_USER + "/registration";

    String API_USER_UPDATE = API_USER + "/update";

    String API_USER_DELETE = API_USER + "/delete";

//    Feign_ENDPOINS

    String THE_MOVIE_DATABASE_API = "https://api.themoviedb.org";

    String THE_MOVIE_DATABASE_API_V3 = THE_MOVIE_DATABASE_API + "/3";

    String THE_MOVIE_DATABASE_API_V3_DISCOVER = THE_MOVIE_DATABASE_API_V3 + "/discover";

    String THE_MOVIE_DATABASE_API_V3_DISCOVER_MOVIE = THE_MOVIE_DATABASE_API_V3_DISCOVER + "/movie";
}
