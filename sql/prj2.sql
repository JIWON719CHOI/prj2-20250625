USE
    prj2;

#인덱스 추가: 검색과 조인을 빠르게 하려고.

# 회원 : 로그인, 가입 권한 등
CREATE TABLE member
(
    id         VARCHAR(255)                                                                 NOT NULL,
    password   VARCHAR(255)                                                                 NOT NULL,
    name       VARCHAR(255)                                                                 NOT NULL,
    info       VARCHAR(1000)                                                                NULL,
    created_at DATETIME               DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at DATETIME               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    role       ENUM ('USER', 'ADMIN') DEFAULT 'USER'                                        NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

ALTER TABLE member
    MODIFY role VARCHAR(255) NOT NULL DEFAULT 'USER';


#게시판 : 공지, 일반 비공개 구분, author_name 은 복사용
CREATE TABLE board
(
    id          INT AUTO_INCREMENT                                                                          NOT NULL,
    title       VARCHAR(255)                                                                                NOT NULL,
    content     TEXT                                                                                        NOT NULL,
    author_id   VARCHAR(255)                                                                                NOT NULL,
    author_name VARCHAR(255)                                                                                NOT NULL,
    created_at  DATETIME                              DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at  DATETIME                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    type        ENUM ('NOTICE', 'GENERAL', 'PRIVATE') DEFAULT 'GENERAL'                                     NOT NULL,
    INDEX idx_board_author_id (author_id),
    CONSTRAINT pk_board PRIMARY KEY (id),
    CONSTRAINT fk_board FOREIGN KEY (author_id) REFERENCES member (id)
);

#댓글 : 단순 댓글이라 type 까지는 필요 없어
CREATE TABLE comment
(
    id          INT AUTO_INCREMENT                                             NOT NULL,
    content     VARCHAR(1000)                                                  NOT NULL,
    board_id    INT                                                            NOT NULL,
    author_id   VARCHAR(255)                                                   NOT NULL,
    author_name VARCHAR(255)                                                   NOT NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    INDEX idx_comment_author_id (author_id),
    CONSTRAINT pk_comment PRIMARY KEY (id),
    CONSTRAINT fk_comment FOREIGN KEY (author_id) REFERENCES member (id)
);

#채팅 :
CREATE TABLE chat
(
    id          INT AUTO_INCREMENT                 NOT NULL,
    message     TEXT                               NOT NULL,
    author_id   VARCHAR(255)                       NOT NULL,
    author_name VARCHAR(255)                       NOT NULL,
    send_at     DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    INDEX idx_chat_author_id (author_id),
    CONSTRAINT pk_chat PRIMARY KEY (id),
    CONSTRAINT fk_chat FOREIGN KEY (author_id) REFERENCES member (id)
);

#반응 : 붐업, 붐따, 신고, 북마크. 유저가 하나의 글에 한 종류의 반응만 1개만 하도록 중복 방지
CREATE TABLE reaction
(
    id         INT AUTO_INCREMENT                                NOT NULL,
    member_id  VARCHAR(255)                                      NOT NULL,
    board_id   INT                                               NOT NULL,
    reacted_at DATETIME DEFAULT CURRENT_TIMESTAMP                NOT NULL,
    type       ENUM ('BOOMUP', 'BOOMDOWN', 'REPORT', 'BOOKMARK') NOT NULL,
    UNIQUE KEY uniq_reaction (type, member_id, board_id),
    INDEX idx_reaction_board_type (board_id, type),
    INDEX idx_reaction_member_id (member_id),
    CONSTRAINT pk_reaction PRIMARY KEY (id),
    CONSTRAINT fk_reaction_board FOREIGN KEY (board_id) REFERENCES board (id),
    CONSTRAINT fk_reaction_member FOREIGN KEY (member_id) REFERENCES member (id)
);

#리포트 : 신고 내역 관리용 (게시글, 댓글 둘다!) 게시글/댓글 둘 다를 대상으로 하기 때문에 target_id 에는 외래키를 걸지 않음
CREATE TABLE report
(
    id          INT AUTO_INCREMENT                                             NOT NULL,
    reason      TEXT                                                           NOT NULL,
    target_id   INT                                                            NOT NULL, -- 게시글 or 댓글의 ID
    reporter_id VARCHAR(255)                                                   NOT NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP                             NOT NULL,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    type        ENUM ('BOARD', 'COMMENT')                                      NOT NULL,
    INDEX idx_report_target_id (target_id),
    CONSTRAINT pk_report PRIMARY KEY (id),
    CONSTRAINT fk_report_member FOREIGN KEY (reporter_id) REFERENCES member (id)
);

DROP TABLE member;
DROP TABLE board;
DROP TABLE comment;
DROP TABLE chat;
DROP TABLE reaction;
DROP TABLE report;
