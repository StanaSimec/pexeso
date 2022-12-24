package cz.czechitas.pexeso.model;

public enum PexesoImage {
        ONE("one.png"),
        TWO("two.png"),
        THREE("three.png"),
        FOUR("four.png"),
        FIVE("five.png"),
        SIX("six.png"),
        SEVEN("seven.png"),
        EIGHT("eight.png"),
        NINE("nine.png"),
        TEN("ten.png");
        public final String path;
        private PexesoImage(String path) {
            this.path = path;
        }
};
