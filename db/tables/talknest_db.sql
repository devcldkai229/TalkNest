CREATE TABLE Users (
	Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	Username VARCHAR(50) NOT NULL UNIQUE,
	Password VARCHAR(50) NOT NULL,
	Email VARCHAR(100) NOT NULL UNIQUE,
	AuthProvider VARCHAR(255) NOT NULL,
	Enabled BOOLEAN DEFAULT TRUE,
	AccountNonExpired BOOLEAN DEFAULT TRUE,
	AccountNonLocked BOOLEAN  DEFAULT TRUE,
	CredentialsNonExpired BOOLEAN DEFAULT TRUE,
	IsVerified BOOLEAN DEFAULT FALSE,
	CreatedAt TIMESTAMP DEFAULT NOW(),
	UpdatedAt TIMESTAMP NULL,
);

CREATE TABLE Role (
	Id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name VARCHAR(100) NOT NULL UNIQUE,
	Description VARCHAR(255) DEFAULT '#NoData'
);

CREATE TABLE Permission (
    id INTEGGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(MAX) DEFAULT '#NoData'
);

CREATE TABLE RolePermission (
	Id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	RoleId INTEGER NOT NULL,
	PermissionId INTEGER NOT NULL,
	FOREIGN KEY (RoleId) REFERENCES Role (Id),
	FOREIGN KEY (PermissionId) REFERENCES Permisson (Id)
);

CREATE TABLE UserRole (
	Id BIGSERIAL PRIMARY KEY,
	UserId UUID NOT NULL,
	RoleId INTEGER NOT NULL,
	FOREIGN KEY (UserId) REFERENCES Users (Id),
	FOREIGN KEY (RoleId) REFERENCES Role (Id),
	CONSTRAINT UniqueUserRole UNIQUE (UserId, RoleId)
);

CREATE TABLE Follower (
	Id BIGSERIAL PRIMARY KEY,
	FollowerId UUID NOT NULL,
	FollowedId UUID NOT NULL,
	FOREIGN KEY (FollowerId) REFERENCES Users (Id),
	FOREIGN KEY (FollowedId) REFERENCES Users (Id)
);

CREATE TABLE UserProfile (
	UserId UUID PRIMARY KEY,
	Bio TEXT NULL,
	Address TEXT NULL,
	DayOfBirth DATE NULL,
	PhoneNumber VARCHAR(20) NULL,
	Gender VARCHAR(50) NULL,
	AvatarUrl VARCHAR(MAX),
	LastUpdated TIMESTAMP DEFAULT NOW(),
	FOREIGN KEY (UserId) REFERENCES Users (Id)
);

CREATE TABLE Group (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Name TEXT NOT NULL,
    Description TEXT NULL,
    PrivacyLevel VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',  -- 'PUBLIC', 'PRIVATE'
    AvatarGroupUrl VARCHAR(MAX) NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    CreatorId UUID NOT NULL,
    IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (CreatorId) REFERENCES UsersId (id)
);

CREATE TABLE ManagerGroup (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    GroupId UUID NOT NULL,
    ManagerId UUID NOT NULL,
    Role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',  -- 'ADMIN', 'MODERATOR'
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (ManagerId) REFERENCES Users (Id),
    FOREIGN KEY (GroupId) REFERENCES Group (Id),
    CONSTRAINT UniqueManagerGroup UNIQUE(GroupId, ManagerId)
);

CREATE TABLE MembersGroup (
    Id BIGSERIAL PRIMARY KEY,
    GroupId UUID NOT NULL,
    MemberId UUID NOT NULL,
    Status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',  -- 'ACTIVE', 'PENDING', 'BANNED'
    JoinedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    InvitedBy UUID NULL,
    FOREIGN KEY (MemberId) REFERENCES Users (Id),
    FOREIGN KEY (GroupId) REFERENCES Group (Id),
    FOREIGN KEY (InvitedBy) REFERENCES Users (Id),
    CONSTRAINT UniqueGroupMembers UNIQUE(GroupId, MemberId)
);

CREATE TABLE GroupInvitation (
    id BIGSERIAL PRIMARY KEY,
    GroupId UUID NOT NULL,
    InviterId UUID NOT NULL,
    InviteeId UUID NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- 'PENDING', 'ACCEPTED', 'DECLINED'
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    FOREIGN KEY (GroupId) REFERENCES Group (Id),
    FOREIGN KEY (InviterId) REFERENCES Users (Id),
    FOREIGN KEY (InviteeId) REFERENCES Users (Id),
    CONSTRAINT uqgroupinvitation UNIQUE(groupid, inviteeid)
);

CREATE TABLE Post (
    id BIGSERIAL PRIMARY KEY,
    UserId UUID NOT NULL,
    Content TEXT NULL,
    PostType VARCHAR(20) NOT NULL,  -- 'TEXT', 'IMAGE', 'VIDEO', 'LINK'
    PrivacyLevel VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',  -- 'PUBLIC', 'FRIENDS', 'PRIVATE'
    GroupId UUID NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    FOREIGN KEY (GroupId) REFERENCES Group (Id)
);

CREATE TABLE EmojiType (
    Id BIGSERIAL PRIMARY KEY,
    Emoji VARCHAR(255) NOT NULL UNIQUE,
    Description TEXT DEFAULT '#NoData'
);

CREATE TABLE Comment (
    Id BIGSERIAL PRIMARY KEY,
    PostId BIGINT NOT NULL,
    UserId UUID NOT NULL,
    Content TEXT NOT NULL,
    ParentCommentId BIGINT NULL,  
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (PostId) REFERENCES Post (Id),
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    FOREIGN KEY (ParentCommentId) REFERENCES Comment (Id)
);

CREATE TABLE PostInteraction (
    Id BIGSERIAL PRIMARY KEY,
    PostId BIGINT NOT NULL,
    UserId UUID NOT NULL,
    EmojiId BIGINT NOT NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (PostId) REFERENCES Post (id),
    FOREIGN KEY (UserId) REFERENCES Users( id),
    FOREIGN KEY (EmojiId) REFERENCES EmojiType (id),
    CONSTRAINT UniquePostUser UNIQUE (PostId, UserId)
);

CREATE TABLE commentinteraction (
    id BIGSERIAL PRIMARY KEY,
    commentid BIGINT NOT NULL,
    userid UUID NOT NULL,
    emojiid BIGINT NOT NULL,
    createdat TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (commentid) REFERENCES comment(id),
    FOREIGN KEY (userid) REFERENCES users(id),
    FOREIGN KEY (emojiid) REFERENCES emojitype(id),
    CONSTRAINT uqcommentuserinteraction UNIQUE(commentid, userid)
);

CREATE TABLE HashTag (
    Id BIGSERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT UniqueHashTag UNIQUE (Name)
);

CREATE TABLE PostHashTag (
	Id BIGSERIAL PRIMARY KEY,
    PostId BIGINT NOT NULL,
    HashTagId BIGINT NOT NULL,
    FOREIGN KEY (PostId) REFERENCES Post (Id),
    FOREIGN KEY (HashTagId) REFERENCES HashTag (Id)
);

CREATE TABLE FileMedia (
    Id BIGSERIAL PRIMARY KEY,
    ContentId BIGINT NOT NULL,  -- Can be postid, messageid, etc.
    ContentType VARCHAR(20) NOT NULL,  -- 'POST', 'MESSAGE', 'COMMENT', etc.
    FileType VARCHAR(20) NOT NULL,  -- 'IMAGE', 'VIDEO', 'DOCUMENT', etc.
    FileName VARCHAR(255) NOT NULL,
    FileSize BIGINT NOT NULL,
    CloudinaryPublicId VARCHAR(255) NOT NULL UNIQUE,
    CloudinaryUrl VARCHAR(500) NOT NULL,
    CloudinarySecureUrl VARCHAR(500) NOT NULL,
    Width INT NULL,  -- For images/videos
    Height INT NULL,  -- For images/videos
    Duration INT NULL,  -- For videos/audio (in seconds)
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
);

CREATE TABLE Conversation (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Type VARCHAR(20) NOT NULL,  -- 'DIRECT', 'GROUP'
    Name TEXT NULL,  -- For group chats
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    CreatorId UUID NOT NULL,
    IsActive BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (CreatorId) REFERENCES Users (Id)
);

CREATE TABLE ConversationParticipants (
    Id BIGSERIAL PRIMARY KEY,
    ConversationId UUID NOT NULL ,
    UserId UUID NOT NULL,
    JoinedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    LeftAt TIMESTAMP NULL,
    IsActive BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (conversationid) REFERENCES Conversation (Id),
    FOREIGN KEY (userid) REFERENCES Users (Id),
    CONSTRAINT UniqueConversationUser UNIQUE(ConversationId, UserId)
);

CREATE TABLE Messages ( -- thuộc về conversation chứa các tin nhắn đoạn chat
    Id BIGSERIAL PRIMARY KEY,
    ConversationId UUID NOT NULL,
    SenderId UUID NOT NULL,
    Content TEXT NULL,
    MessageType VARCHAR(20) NOT NULL DEFAULT 'TEXT',  -- 'TEXT', 'IMAGE', 'VIDEO', 'FILE', 'LOCATION'
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
    IsEdited BOOLEAN NOT NULL DEFAULT FALSE,
    IsSystemMessage BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (ConversationId) REFERENCES conversation (Id),
    FOREIGN KEY (SenderId) REFERENCES Users (Id)
);

CREATE TABLE MessageStatuses (
    Id BIGSERIAL PRIMARY KEY,
    MessageId BIGINT NOT NULL,
    UserId UUID NOT NULL,
    Status VARCHAR(20) NOT NULL,  -- 'DELIVERED', 'READ'
    StatusTimestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (MessageId) REFERENCES Messages (Id),
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    CONSTRAINT UniqueMessasgeUser UNIQUE(MessageId, UserId)
);

CREATE TABLE NotificationType (
    Id BIGSERIAL PRIMARY KEY,
    Code VARCHAR(50) NOT NULL UNIQUE,  -- 'LIKE', 'COMMENT', 'FOLLOW', 'MESSAGE', etc.
    Description TEXT NOT NULL,
    Template TEXT NULL  -- Template for notification text with placeholders
);

CREATE TABLE Notification (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    UserUd UUID NOT NULL,  -- Recipient
    TypeId BIGINT NOT NULL,
    SenderId UUID NULL,
    EntityId VARCHAR(100) NULL,  -- ID of related entity (post, comment, etc.)
    EntityType VARCHAR(50) NULL,  -- Type of related entity ('POST', 'COMMENT', etc.)
    AdditionaldData TEXT NULL,  -- JSON with additional context data
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    IsDelivered BOOLEAN NOT NULL DEFAULT FALSE,
    DeliveryAttempts INT NOT NULL DEFAULT 0,
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    FOREIGN KEY (SenderId) REFERENCES Users (Id),
    FOREIGN KEY (TypeId) REFERENCES NotificationType (Id)
);







