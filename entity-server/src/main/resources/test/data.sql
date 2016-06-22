
INSERT INTO user ( id, uid, type ) VALUES
  ( 1, 'user-uid-1', 1 ),
  ( 2, 'user-uid-2', 0 ),
  ( 3, 'user-uid-3', 0 ),
  ( 4, 'user-uid-4', 0 ),
  ( 5, 'user-uid-5', 0 ),
  ( 6, 'user-uid-6', 0 ),
  ( 7, 'user-uid-7', 0 );

INSERT INTO internet_entity ( id, uuid, name, ip, creator_id ) VALUES
  ( 1, 'entity-uuid-1', 'entity1', '0.0.0.1', 1 ),
  ( 2, 'entity-uuid-2', 'entity2', '0.0.0.2', 1 ),
  ( 3, 'entity-uuid-3', 'entity3', '0.0.0.3', 1 );

INSERT INTO authorization_token ( id, token, entity_id, creator_id, expired_at ) VALUES
  ( 1, '12345', 1, 1, '2050-01-01' ),
  ( 2, '12345', 2, 2, '2050-01-01' ),
  ( 3, '12345', 3, 3, '1999-01-01' ),
  ( 4, '12345', 3, 4, '1999-01-01' );

INSERT INTO entity_authorization ( id, authorizee_id, authorizer_id, entity_id ) VALUES
( 1, 1, 1, 1 ),
( 2, 2, 1, 2 ),
( 3, 3, 1, 3 ),
( 4, 4, 1, 3 ),
( 5, 5, 1, 3 );