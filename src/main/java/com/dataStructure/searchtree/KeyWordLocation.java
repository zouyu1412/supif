package com.dataStructure.searchtree;

import java.io.Serializable;

public class KeyWordLocation implements Serializable {
        int start;
        int end;
        String word;
        String url;

        KeyWordLocation(int start, int end, String word,String url){
            this.start = start;
            this.end = end;
            this.word = word;
            this.url = url;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "KeyWordLocation{" +
                    "start=" + start +
                    ", end=" + end +
                    ", word='" + word + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }