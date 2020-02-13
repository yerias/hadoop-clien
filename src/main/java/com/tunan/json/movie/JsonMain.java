package com.tunan.json.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * @description:
 * @author: tunan
 * @create: 2020-02-09 19:01
 * @since: 1.0.0
 **/
public class JsonMain {

        public static void main(String[] args) throws IOException {

            String jsonS = "{\"movie\":\"1\",\"rate\":\"5\",\"time\":\"978300760\",\"userId\":\"1\"}";

            JsonMovie json = new JsonMovie("1", "5", "978300760", "1");
            ObjectMapper mapper = new ObjectMapper();
            /*String s = mapper.writeValueAsString(json);
            System.out.println(s);*/
            JsonMovie movie = mapper.readValue(jsonS, JsonMovie.class);
            System.out.println(movie.toString());
        }
}

