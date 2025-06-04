CREATE TABLE IF NOT EXISTS Users (
	Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	Username VARCHAR(100) NOT NULL UNIQUE,
	Password VARCHAR(500) NOT NULL,
	Email VARCHAR(100) NOT NULL UNIQUE,
	AuthProvider VARCHAR(255) NOT NULL,
	Enabled BOOLEAN DEFAULT TRUE,
	AccountNonExpired BOOLEAN DEFAULT TRUE,
	AccountNonLocked BOOLEAN  DEFAULT TRUE,
	CredentialsNonExpired BOOLEAN DEFAULT TRUE,
	IsVerified BOOLEAN DEFAULT FALSE,
	CreatedAt TIMESTAMP DEFAULT NOW(),
	UpdatedAt TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS Invalidate_Token (
    Id VARCHAR(500) PRIMARY KEY,
    Token VARCHAR(1000) UNIQUE,
    ExpiryAt TIMESTAMP
)

CREATE TABLE IF NOT EXISTS Refresh_Token (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    UserId UUID NOT NULL,
    Token VARCHAR(500) NOT NULL UNIQUE,
    ExpiredDate TIMESTAMP NOT NULL,
    CreatedAt TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (UserId) REFERENCES Users (Id)
);

CREATE TABLE IF NOT EXISTS Role (
	Id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	Name VARCHAR(100) NOT NULL UNIQUE,
	Description TEXT DEFAULT '#NoData'
);

CREATE TABLE IF NOT EXISTS Permission (
    Id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Name VARCHAR(255) NOT NULL UNIQUE,
    Description TEXT DEFAULT '#NoData'
);

CREATE TABLE IF NOT EXISTS Role_Permission (
	Id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	RoleId INTEGER NOT NULL,
	PermissionId INTEGER NOT NULL,
	FOREIGN KEY (RoleId) REFERENCES Role (Id),
	FOREIGN KEY (PermissionId) REFERENCES Permission (Id)
);

CREATE TABLE IF NOT EXISTS User_Role (
	Id BIGSERIAL PRIMARY KEY,
	UserId UUID NOT NULL,
	RoleId INTEGER NOT NULL,
	FOREIGN KEY (UserId) REFERENCES Users (Id),
	FOREIGN KEY (RoleId) REFERENCES Role (Id),
	CONSTRAINT UniqueUserRole UNIQUE (UserId, RoleId)
);

CREATE TABLE IF NOT EXISTS User_Profile (
	UserId UUID PRIMARY KEY,
	FirstName VARCHAR(255),
	LastName VARCHAR(255),
	Bio TEXT NULL,
	Address TEXT NULL,
	DayOfBirth DATE NULL,
	PhoneNumber VARCHAR(20) NULL,
	Gender VARCHAR(50) NULL,
	AvatarUrl VARCHAR(500),
	LastUpdated TIMESTAMP DEFAULT NOW(),
	FOREIGN KEY (UserId) REFERENCES Users (Id)
);

CREATE TABLE Verification_Token (
    Token VARCHAR(500) PRIMARY KEY,
    UserId UUID,
    CreatedAt TIMESTAMP DEFAULT NOW(),
    ExpiredAt TIMESTAMP,
    FOREIGN KEY (UserId) REFERENCES Users (Id)
)

CREATE TABLE IF NOT EXISTS Follower (
	Id BIGSERIAL PRIMARY KEY,
	FollowerId UUID NOT NULL,
	FollowedId UUID NOT NULL,
	FOREIGN KEY (FollowerId) REFERENCES Users (Id),
	FOREIGN KEY (FollowedId) REFERENCES Users (Id)
);

CREATE TABLE IF NOT EXISTS Blocked {
    Id BIGSERIAL PRIMARY KEY,
	BlockerId UUID NOT NULL,
	BlockedId UUID NOT NULL,
	CreatedAt TIMESTAMP DEFAULT NOW(),
	FOREIGN KEY (BlockerId) REFERENCES Users (Id),
	FOREIGN KEY (BlockedId) REFERENCES Users (Id)
}

CREATE TABLE IF NOT EXISTS Groups (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Name TEXT NOT NULL,
    Description TEXT NULL,
    PrivacyLevel VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',  -- 'PUBLIC', 'PRIVATE'
    AvatarGroupUrl VARCHAR(500) NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    CreatorId UUID NOT NULL,
    IsDeleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (CreatorId) REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS ManagerGroup (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    GroupId UUID NOT NULL,
    ManagerId UUID NOT NULL,
    Role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',  -- 'ADMIN', 'MODERATOR'
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (ManagerId) REFERENCES Users (Id),
    FOREIGN KEY (GroupId) REFERENCES Groups (Id),
    CONSTRAINT UniqueManagerGroup UNIQUE(GroupId, ManagerId)
);

CREATE TABLE IF NOT EXISTS MembersGroup (
    Id BIGSERIAL PRIMARY KEY,
    GroupId UUID NOT NULL,
    MemberId UUID NOT NULL,
    Status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',  -- 'ACTIVE', 'PENDING', 'BANNED'
    JoinedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    InvitedBy UUID NULL,
    FOREIGN KEY (MemberId) REFERENCES Users (Id),
    FOREIGN KEY (GroupId) REFERENCES Groups (Id),
    FOREIGN KEY (InvitedBy) REFERENCES Users (Id),
    CONSTRAINT UniqueGroupMembers UNIQUE(GroupId, MemberId)
);

CREATE TABLE IF NOT EXISTS GroupInvitation (
    id BIGSERIAL PRIMARY KEY,
    GroupId UUID NOT NULL,
    InviterId UUID NOT NULL,
    InviteeId UUID NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- 'PENDING', 'ACCEPTED', 'DECLINED'
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    FOREIGN KEY (GroupId) REFERENCES Groups (Id),
    FOREIGN KEY (InviterId) REFERENCES Users (Id),
    FOREIGN KEY (InviteeId) REFERENCES Users (Id),
    CONSTRAINT UniqueGroupInvitee UNIQUE(GroupId, InviteeId)
);

CREATE TABLE IF NOT EXISTS Post (
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
    FOREIGN KEY (GroupId) REFERENCES Groups (Id)
);

CREATE TABLE IF NOT EXISTS EmojiType (
    Id BIGSERIAL PRIMARY KEY,
    Emoji VARCHAR(255) NOT NULL UNIQUE,
    Description TEXT DEFAULT '#NoData'
);

CREATE TABLE IF NOT EXISTS Comment (
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

CREATE TABLE IF NOT EXISTS PostInteraction (
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

CREATE TABLE IF NOT EXISTS CommentInteraction (
    Id BIGSERIAL PRIMARY KEY,
    CommentId BIGINT NOT NULL,
    UserId UUID NOT NULL,
    EmojiId BIGINT NOT NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (CommentId) REFERENCES Comment (id),
    FOREIGN KEY (UserId) REFERENCES Users (id),
    FOREIGN KEY (EmojiId) REFERENCES EmojiType (id),
    CONSTRAINT UniqueCommentUser UNIQUE(CommentId, UserId)
);

CREATE TABLE IF NOT EXISTS HashTag (
    Id BIGSERIAL PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT UniqueHashTag UNIQUE (Name)
);

CREATE TABLE IF NOT EXISTS PostHashTag (
	Id BIGSERIAL PRIMARY KEY,
    PostId BIGINT NOT NULL,
    HashTagId BIGINT NOT NULL,
    FOREIGN KEY (PostId) REFERENCES Post (Id),
    FOREIGN KEY (HashTagId) REFERENCES HashTag (Id)
);

CREATE TABLE IF NOT EXISTS FileMedia (
    Id BIGSERIAL PRIMARY KEY,
    ContentId BIGINT,  -- Can be postid, messageid, etc.
    ContentType VARCHAR(50),  -- 'POST', 'MESSAGE', 'COMMENT', etc.
    FileType VARCHAR(50),  -- 'IMAGE', 'VIDEO', 'DOCUMENT', etc.
    FileName VARCHAR(255) DEFAULT '#NoData',
    Format VARCHAR(50),
    CloudinaryPublicId VARCHAR(500) UNIQUE,
    CloudinaryUrl VARCHAR(500),
    CloudinarySecureUrl VARCHAR(500),
    Width INT NULL,  -- For images/videos
    Height INT NULL,  -- For images/videos
    Duration INT NULL,  -- For videos/audio (in seconds)
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS Conversation (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Type VARCHAR(20) NOT NULL,  -- 'DIRECT', 'GROUP'
    Name TEXT NULL,  -- For group chats
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    UpdatedAt TIMESTAMP NULL,
    CreatorId UUID NOT NULL,
    IsActive BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (CreatorId) REFERENCES Users (Id)
);

CREATE TABLE IF NOT EXISTS ConversationParticipants (
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

CREATE TABLE IF NOT EXISTS Messages ( -- thuộc về conversation chứa các tin nhắn đoạn chat
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

CREATE TABLE IF NOT EXISTS MessageStatuses (
    Id BIGSERIAL PRIMARY KEY,
    MessageId BIGINT NOT NULL,
    UserId UUID NOT NULL,
    Status VARCHAR(20) NOT NULL,  -- 'DELIVERED', 'READ'
    StatusTimestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (MessageId) REFERENCES Messages (Id),
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    CONSTRAINT UniqueMessasgeUser UNIQUE(MessageId, UserId)
);

CREATE TABLE IF NOT EXISTS NotificationType (
    Id BIGSERIAL PRIMARY KEY,
    Code VARCHAR(50) NOT NULL UNIQUE,  -- 'LIKE', 'COMMENT', 'FOLLOW', 'MESSAGE', etc.
    Description TEXT NOT NULL,
    Template TEXT NULL  -- Template for notification text with placeholders
);

CREATE TABLE IF NOT EXISTS Notification (
    Id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    UserId UUID NOT NULL,  -- Recipient
    TypeId BIGINT NOT NULL,
    SenderId UUID NULL,
    EntityId VARCHAR(100) NULL,  -- ID of related entity (post, comment, etc.)
    EntityType VARCHAR(50) NULL,  -- Type of related entity ('POST', 'COMMENT', etc.)
    AdditionalData TEXT NULL,  -- JSON with additional context data
    CreatedAt TIMESTAMP NOT NULL DEFAULT NOW(),
    IsDelivered BOOLEAN NOT NULL DEFAULT FALSE,
    DeliveryAttempts INT NOT NULL DEFAULT 0,
    FOREIGN KEY (UserId) REFERENCES Users (Id),
    FOREIGN KEY (SenderId) REFERENCES Users (Id),
    FOREIGN KEY (TypeId) REFERENCES NotificationType (Id)
);

-- Partition large tables
CREATE TABLE Post_2024 PARTITION OF Post
FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');

-- Add materialized views for analytics
CREATE MATERIALIZED VIEW user_stats AS
SELECT userid, COUNT(*) as post_count,
       COUNT(DISTINCT follower.followerid) as follower_count
FROM post LEFT JOIN follower ON post.userid = follower.followedid
GROUP BY userid;


-- Add check constraints
ALTER TABLE Post ADD CONSTRAINT check_post_type
CHECK (PostType IN ('TEXT', 'IMAGE', 'VIDEO', 'LINK'));

ALTER TABLE Groups ADD CONSTRAINT check_privacy_level
CHECK (PrivacyLevel IN ('PUBLIC', 'PRIVATE'));