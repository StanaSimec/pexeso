DROP TABLE IF EXISTS round;

DROP TABLE IF EXISTS card;

DROP TABLE IF EXISTS image;

DROP TABLE IF EXISTS board;

CREATE TABLE board (
    id INT NOT NULL AUTO_INCREMENT,
    player VARCHAR(50),
    hash VARCHAR(250),
    PRIMARY KEY (id)
);

CREATE TABLE image (
    id INT NOT NULL AUTO_INCREMENT,
    imagePath varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE card (
    id INT NOT NULL AUTO_INCREMENT,
    boardId INT NOT NULL,
    imageId INT NOT NULL,
    selected BOOLEAN NOT NULL DEFAULT FALSE,
    paired BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (boardId) REFERENCES board (id),
    FOREIGN KEY (imageId) REFERENCES image (id)
);

CREATE TABLE round (
    id INT NOT NULL AUTO_INCREMENT,
    boardId INT NOT NULL,
    firstCardId INT,
    secondCardId INT,
    stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (boardId) REFERENCES board (id),
    FOREIGN KEY (firstCardId) REFERENCES card (id),
    FOREIGN KEY (secondCardId) REFERENCES card (id)
);