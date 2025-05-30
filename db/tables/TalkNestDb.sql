CREATE TABLE users (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NULL,
    first_name NVARCHAR(50) NULL,
    last_name NVARCHAR(50) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    last_login DATETIME NULL,
    is_active BIT NOT NULL DEFAULT 1,
    is_verified BIT NOT NULL DEFAULT 0
);

CREATE TABLE oauth_connection (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    user_id UNIQUEIDENTIFIER NOT NULL,
    provider VARCHAR(20) NOT NULL,  -- 'GOOGLE', 'FACEBOOK', etc.
    provider_user_id VARCHAR(255) NOT NULL,
    access_token VARCHAR(MAX) NULL,
    refresh_token VARCHAR(MAX) NULL,
    token_expires_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    CONSTRAINT UQ_oauth_user_provider UNIQUE(user_id, provider),
    CONSTRAINT UQ_provider_user_id UNIQUE(provider, provider_user_id)
);

CREATE TABLE user_roles (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    user_id UNIQUEIDENTIFIER NOT NULL,
    role_name VARCHAR(20) NOT NULL,  -- 'USER', 'ADMIN', 'MODERATOR'
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    CONSTRAINT UQ_user_role UNIQUE(user_id, role_name)
);

CREATE TABLE followers (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    follower_id UNIQUEIDENTIFIER NOT NULL,
    followed_id UNIQUEIDENTIFIER NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (follower_id) REFERENCES Users(Id),
    FOREIGN KEY (followed_id) REFERENCES Users(Id),
    CONSTRAINT UQ_follower_followed UNIQUE(follower_id, followed_id)
);

-- Profile and Settings
CREATE TABLE user_profile (
    user_id UNIQUEIDENTIFIER PRIMARY KEY,
    bio NTEXT NULL,
    location NVARCHAR(100) NULL,
    website VARCHAR(255) NULL,
    birth_date DATE NULL,
    phone_number VARCHAR(20) NULL,
    gender VARCHAR(20) NULL,
    cover_photo_url VARCHAR(500) NULL,
    profile_picture_url VARCHAR(500) NULL,
    updated_at DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES Users(Id)
);

CREATE TABLE user_setting (
    user_id UNIQUEIDENTIFIER PRIMARY KEY,
    notification_preferences NVARCHAR(MAX) NULL,  -- JSON string for preferences
    privacy_settings NVARCHAR(MAX) NULL,  -- JSON string for settings
    theme VARCHAR(20) DEFAULT 'LIGHT',
    language VARCHAR(10) DEFAULT 'en-US',
    updated_at DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES Users(Id)
);

-- Groups Management
CREATE TABLE groups (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(100) NOT NULL,
    description NTEXT NULL,
    privacy_level VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',  -- 'PUBLIC', 'PRIVATE'
    cover_photo_url VARCHAR(500) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    creator_id UNIQUEIDENTIFIER NOT NULL,
    is_deleted BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (creator_id) REFERENCES Users(Id)
);

CREATE TABLE manager_group (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    group_id UNIQUEIDENTIFIER NOT NULL,
    manager_id UNIQUEIDENTIFIER NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',  -- 'ADMIN', 'MODERATOR'
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (manager_id) REFERENCES Users(Id),
    FOREIGN KEY (group_id) REFERENCES Groups(Id),
    CONSTRAINT UQ_group_manager UNIQUE(group_id, manager_id)
);

CREATE TABLE members_group (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    group_id UNIQUEIDENTIFIER NOT NULL,
    member_id UNIQUEIDENTIFIER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',  -- 'ACTIVE', 'PENDING', 'BANNED'
    joined_at DATETIME NOT NULL DEFAULT GETDATE(),
    invited_by UNIQUEIDENTIFIER NULL,
    FOREIGN KEY (member_id) REFERENCES Users(Id),
    FOREIGN KEY (group_id) REFERENCES Groups(Id),
    FOREIGN KEY (invited_by) REFERENCES Users(Id),
    CONSTRAINT UQ_group_member UNIQUE(group_id, member_id)
);

CREATE TABLE group_invitation (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    group_id UNIQUEIDENTIFIER NOT NULL,
    inviter_id UNIQUEIDENTIFIER NOT NULL,
    invitee_id UNIQUEIDENTIFIER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- 'PENDING', 'ACCEPTED', 'DECLINED'
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    FOREIGN KEY (group_id) REFERENCES Groups(Id),
    FOREIGN KEY (inviter_id) REFERENCES Users(Id),
    FOREIGN KEY (invitee_id) REFERENCES Users(Id),
    CONSTRAINT UQ_group_invitation UNIQUE(group_id, invitee_id)
);

-- Content Management
CREATE TABLE post (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    user_id UNIQUEIDENTIFIER NOT NULL,
    content NTEXT NULL,
    post_type VARCHAR(20) NOT NULL,  -- 'TEXT', 'IMAGE', 'VIDEO', 'LINK'
    privacy_level VARCHAR(20) NOT NULL DEFAULT 'PUBLIC',  -- 'PUBLIC', 'FRIENDS', 'PRIVATE'
    location NVARCHAR(255) NULL,
    group_id UNIQUEIDENTIFIER NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    is_deleted BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (group_id) REFERENCES Groups(Id)
);

CREATE TABLE emoji_type (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    emoji VARCHAR(255) NOT NULL,
    description NTEXT DEFAULT '#NoData',
    CONSTRAINT UQ_emoji UNIQUE(emoji)
);

CREATE TABLE comment (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    content NTEXT NOT NULL,
    parent_comment_id BIGINT NULL,  -- For nested comments
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    is_deleted BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES Post(Id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (parent_comment_id) REFERENCES Comment(Id)
);

CREATE TABLE post_interaction (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    emoji_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (post_id) REFERENCES Post(Id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (emoji_id) REFERENCES emoji_type(Id),
    CONSTRAINT UQ_post_user_interaction UNIQUE(post_id, user_id)
);

CREATE TABLE comment_interaction (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    comment_id BIGINT NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    emoji_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (comment_id) REFERENCES Comment(Id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (emoji_id) REFERENCES emoji_type(Id),
    CONSTRAINT UQ_comment_user_interaction UNIQUE(comment_id, user_id)
);

CREATE TABLE hashtags (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_hashtag_name UNIQUE(name)
);

CREATE TABLE post_hashtags (
    post_id BIGINT NOT NULL,
    hashtag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, hashtag_id),
    FOREIGN KEY (post_id) REFERENCES Post(Id),
    FOREIGN KEY (hashtag_id) REFERENCES hashtags(Id)
);

-- File Management (Cloudinary integration)
CREATE TABLE file_media (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    content_id BIGINT NOT NULL,  -- Can be post_id, message_id, etc.
    content_type VARCHAR(20) NOT NULL,  -- 'POST', 'MESSAGE', 'COMMENT', etc.
    file_type VARCHAR(20) NOT NULL,  -- 'IMAGE', 'VIDEO', 'DOCUMENT', etc.
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT NOT NULL,
    cloudinary_public_id VARCHAR(255) NOT NULL,
    cloudinary_url VARCHAR(500) NOT NULL,
    cloudinary_secure_url VARCHAR(500) NOT NULL,
    width INT NULL,  -- For images/videos
    height INT NULL,  -- For images/videos
    duration INT NULL,  -- For videos/audio (in seconds)
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_cloudinary_public_id UNIQUE(cloudinary_public_id)
);

-- Real-time Chat
CREATE TABLE conversations (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    type VARCHAR(20) NOT NULL,  -- 'DIRECT', 'GROUP'
    name NVARCHAR(100) NULL,  -- For group chats
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    creator_id UNIQUEIDENTIFIER NOT NULL,
    is_active BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (creator_id) REFERENCES Users(Id)
);

CREATE TABLE conversation_participants (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    conversation_id UNIQUEIDENTIFIER NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    joined_at DATETIME NOT NULL DEFAULT GETDATE(),
    left_at DATETIME NULL,
    is_active BIT NOT NULL DEFAULT 1,
    last_read_at DATETIME NULL,
    FOREIGN KEY (conversation_id) REFERENCES conversations(Id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    CONSTRAINT UQ_conversation_participant UNIQUE(conversation_id, user_id)
);

CREATE TABLE Messages (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    conversation_id UNIQUEIDENTIFIER NOT NULL,
    sender_id UNIQUEIDENTIFIER NOT NULL,
    content NTEXT NULL,
    message_type VARCHAR(20) NOT NULL DEFAULT 'TEXT',  -- 'TEXT', 'IMAGE', 'VIDEO', 'FILE', 'LOCATION'
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    is_deleted BIT NOT NULL DEFAULT 0,
    is_edited BIT NOT NULL DEFAULT 0,
    is_system_message BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (conversation_id) REFERENCES conversations(Id),
    FOREIGN KEY (sender_id) REFERENCES Users(Id)
);

CREATE TABLE message_statuses (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    message_id BIGINT NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    status VARCHAR(20) NOT NULL,  -- 'DELIVERED', 'READ'
    status_timestamp DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (message_id) REFERENCES Messages(Id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    CONSTRAINT UQ_message_user_status UNIQUE(message_id, user_id)
);

CREATE TABLE message_reactions (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    message_id BIGINT NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    emoji_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (message_id) REFERENCES Messages(Id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (emoji_id) REFERENCES emoji_type(Id),
    CONSTRAINT UQ_message_user_reaction UNIQUE(message_id, user_id)
);

-- Real-time Notifications
CREATE TABLE notification_types (
    Id BIGINT IDENTITY(1000,1) PRIMARY KEY,
    code VARCHAR(50) NOT NULL,  -- 'LIKE', 'COMMENT', 'FOLLOW', 'MESSAGE', etc.
    description NVARCHAR(255) NOT NULL,
    template NVARCHAR(500) NOT NULL,  -- Template for notification text with placeholders
    CONSTRAINT UQ_notification_code UNIQUE(code)
);

CREATE TABLE Notification (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    user_id UNIQUEIDENTIFIER NOT NULL,  -- Recipient
    type_id BIGINT NOT NULL,
    sender_id UNIQUEIDENTIFIER NULL,
    entity_id NVARCHAR(100) NOT NULL,  -- ID of related entity (post, comment, etc.)
    entity_type VARCHAR(50) NOT NULL,  -- Type of related entity ('POST', 'COMMENT', etc.)
    additional_data NVARCHAR(MAX) NULL,  -- JSON with additional context data
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    is_read BIT NOT NULL DEFAULT 0,
    is_delivered BIT NOT NULL DEFAULT 0,
    delivery_attempts INT NOT NULL DEFAULT 0,
    last_delivery_attempt DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (sender_id) REFERENCES Users(Id),
    FOREIGN KEY (type_id) REFERENCES notification_types(Id)
);

CREATE TABLE notification_settings (
    user_id UNIQUEIDENTIFIER NOT NULL,
    type_id BIGINT NOT NULL,
    is_enabled BIT NOT NULL DEFAULT 1,
    email_enabled BIT NOT NULL DEFAULT 0,
    push_enabled BIT NOT NULL DEFAULT 1,
    updated_at DATETIME NOT NULL DEFAULT GETDATE(),
    PRIMARY KEY (user_id, type_id),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    FOREIGN KEY (type_id) REFERENCES notification_types(Id)
);

CREATE TABLE push_devices (
    Id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    user_id UNIQUEIDENTIFIER NOT NULL,
    device_token VARCHAR(255) NOT NULL,
    device_type VARCHAR(50) NOT NULL,  -- 'ANDROID', 'IOS', 'WEB'
    is_active BIT NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    last_used_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(Id),
    CONSTRAINT UQ_device_token UNIQUE(device_token)
);

-- Create indices for performance optimization
-- User indices
CREATE INDEX IDX_USERS_USERNAME ON Users(username);
CREATE INDEX IDX_USERS_EMAIL ON Users(email);
CREATE INDEX IDX_OAUTH_USER_ID ON oauth_connection(user_id);
CREATE INDEX IDX_OAUTH_PROVIDER ON oauth_connection(provider, provider_user_id);

-- Post indices
CREATE INDEX IDX_POSTS_USER_ID ON Post(user_id);
CREATE INDEX IDX_POSTS_CREATED_AT ON Post(created_at);
CREATE INDEX IDX_POSTS_GROUP_ID ON Post(group_id);

-- File media indices
CREATE INDEX IDX_FILE_MEDIA_CONTENT ON file_media(content_id, content_type);

-- Comment indices
CREATE INDEX IDX_COMMENTS_POST_ID ON Comment(post_id);
CREATE INDEX IDX_COMMENTS_USER_ID ON Comment(user_id);
CREATE INDEX IDX_COMMENTS_PARENT ON Comment(parent_comment_id);

-- Interaction indices
CREATE INDEX IDX_POST_INTERACTION_POST ON Post_Interaction(post_id);
CREATE INDEX IDX_POST_INTERACTION_USER ON Post_Interaction(user_id);
CREATE INDEX IDX_COMMENT_INTERACTION_COMMENT ON Comment_Interaction(comment_id);
CREATE INDEX IDX_COMMENT_INTERACTION_USER ON Comment_Interaction(user_id);

-- Group indices
CREATE INDEX IDX_GROUPS_CREATOR ON Groups(creator_id);
CREATE INDEX IDX_MEMBERS_GROUP_GROUP ON members_group(group_id);
CREATE INDEX IDX_MEMBERS_GROUP_MEMBER ON members_group(member_id);
CREATE INDEX IDX_MANAGER_GROUP_GROUP ON manager_group(group_id);
CREATE INDEX IDX_MANAGER_GROUP_MANAGER ON manager_group(manager_id);

-- Chat indices
CREATE INDEX IDX_MESSAGES_CONVERSATION ON Messages(conversation_id);
CREATE INDEX IDX_MESSAGES_SENDER ON Messages(sender_id);
CREATE INDEX IDX_MESSAGES_CREATED_AT ON Messages(created_at);
CREATE INDEX IDX_CONVERSATION_PARTICIPANTS_USER ON conversation_participants(user_id);
CREATE INDEX IDX_CONVERSATION_PARTICIPANTS_CONVERSATION ON conversation_participants(conversation_id);
CREATE INDEX IDX_MESSAGE_STATUSES_MESSAGE ON message_statuses(message_id);
CREATE INDEX IDX_MESSAGE_REACTIONS_MESSAGE ON message_reactions(message_id);

-- Notification indices
CREATE INDEX IDX_NOTIFICATIONS_USER ON Notification(user_id);
CREATE INDEX IDX_NOTIFICATIONS_TYPE ON Notification(type_id);
CREATE INDEX IDX_NOTIFICATIONS_CREATED_AT ON Notification(created_at);
CREATE INDEX IDX_NOTIFICATIONS_UNREAD ON Notification(user_id, is_read);
CREATE INDEX IDX_PUSH_DEVICES_USER ON push_devices(user_id);