-- ============================================================================
-- Seed test data for Cart endpoint testing (Phase 1)
--
-- Run this MANUALLY in DBeaver (or psql) AFTER the app has started at least
-- once and food_delivery.sql has created the schema. This file is NOT loaded
-- automatically by Spring Boot — spring.sql.init.schema-locations is pinned
-- to food_delivery.sql only.
--
-- All inserts are idempotent: re-running this script is safe.
--
-- ─── USE THESE IDs IN POSTMAN ────────────────────────────────────────────────
--   customerId   = 11111111-1111-1111-1111-111111111111
--   restaurantId = 33333333-3333-3333-3333-333333333333
--   branchId     = 44444444-4444-4444-4444-444444444444
--
--   menuItemId 1 = 66666666-6666-6666-6666-666666666666   Cheeseburger     75.00
--   menuItemId 2 = 77777777-7777-7777-7777-777777777777   Margherita Pizza 120.00
--   menuItemId 3 = 88888888-8888-8888-8888-888888888888   Caesar Salad      55.00
-- ============================================================================


------------------------------------------------------------------------------
-- 1. user_type — users.user_type_id is NOT NULL
------------------------------------------------------------------------------
INSERT INTO user_type (user_type_name)
SELECT 'CUSTOMER'
WHERE NOT EXISTS (SELECT 1 FROM user_type WHERE user_type_name = 'CUSTOMER');


------------------------------------------------------------------------------
-- 2. users — the underlying account that the customer wraps
------------------------------------------------------------------------------
INSERT INTO users (
    user_id, user_type_id, user_first_name, user_last_name,
    user_phone, user_email, user_password
)
VALUES (
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
    (SELECT user_type_id FROM user_type WHERE user_type_name = 'CUSTOMER' LIMIT 1),
    'Test', 'Customer',
    '+201000000001', 'test.customer@example.com',
    'not-a-real-hash-placeholder'
)
ON CONFLICT (user_email) DO NOTHING;


------------------------------------------------------------------------------
-- 3. customer — the entity referenced by Cart.customer
------------------------------------------------------------------------------
INSERT INTO customer (customer_id, customer_user_id)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1'
)
ON CONFLICT (customer_user_id) DO NOTHING;


------------------------------------------------------------------------------
-- 4. restaurant — top-level brand
------------------------------------------------------------------------------
INSERT INTO restaurant (restaurant_id, restaurant_name, restaurant_description)
SELECT '33333333-3333-3333-3333-333333333333',
       'Test Restaurant',
       'For cart endpoint testing'
WHERE NOT EXISTS (
    SELECT 1 FROM restaurant
    WHERE restaurant_id = '33333333-3333-3333-3333-333333333333'
);


------------------------------------------------------------------------------
-- 5. restaurant_branch — physical branch the cart will be tied to
------------------------------------------------------------------------------
INSERT INTO restaurant_branch (
    branch_id, branch_rest_id, branch_delivery_fee, branch_min_order,
    branch_city, branch_open_time, branch_close_time, branch_phone_number,
    branch_estimated_delivery_time, created_by
)
SELECT
    '44444444-4444-4444-4444-444444444444',
    '33333333-3333-3333-3333-333333333333',
    10.00, 50.00,
    'Cairo', '09:00:00', '23:00:00', '+201111111111',
    30,
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1'
WHERE NOT EXISTS (
    SELECT 1 FROM restaurant_branch
    WHERE branch_id = '44444444-4444-4444-4444-444444444444'
);


------------------------------------------------------------------------------
-- 6. restaurant_menu — owned by the branch
--
-- HEADS-UP: food_delivery.sql declares this column as `restaurant_menu_rest_id`
-- but the RestaurantMenu entity expects `restaurant_menu_rest_branch_id`. If
-- your DB uses the entity name, change the column in this INSERT accordingly.
-- The VALUE inserted is the BRANCH id either way (because Cart.addItem walks
-- menu → branch).
------------------------------------------------------------------------------
INSERT INTO restaurant_menu (
    restaurant_menu_id, restaurant_menu_rest_id, restaurant_menu_name, created_by
)
SELECT
    '55555555-5555-5555-5555-555555555555',
    '44444444-4444-4444-4444-444444444444',
    'Main Menu',
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1'
WHERE NOT EXISTS (
    SELECT 1 FROM restaurant_menu
    WHERE restaurant_menu_id = '55555555-5555-5555-5555-555555555555'
);


------------------------------------------------------------------------------
-- 7. menu_item — three items so you can exercise add / update / delete
------------------------------------------------------------------------------
INSERT INTO menu_item (
    menu_item_id, restaurant_menu_id,
    menu_item_name, menu_item_description, menu_item_price,
    created_by
)
VALUES
    ('66666666-6666-6666-6666-666666666666',
     '55555555-5555-5555-5555-555555555555',
     'Cheeseburger',     'Classic beef burger',          75.00,
     'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1'),

    ('77777777-7777-7777-7777-777777777777',
     '55555555-5555-5555-5555-555555555555',
     'Margherita Pizza', 'Tomato, mozzarella, basil',   120.00,
     'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1'),

    ('88888888-8888-8888-8888-888888888888',
     '55555555-5555-5555-5555-555555555555',
     'Caesar Salad',     'Romaine, croutons, dressing',  55.00,
     'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1')
ON CONFLICT (menu_item_id) DO NOTHING;


INSERT INTO order_status (order_status_name, order_status_description) VALUES
    ('PENDING',          'Order placed, awaiting restaurant confirmation'),
    ('CONFIRMED',        'Restaurant has accepted the order'),
    ('PREPARING',        'Order is being prepared in the kitchen'),
    ('FINISHED',         'Order is finished'),
    ('REJECTED',         'Order is rejected by restaurant'),
    ('CANCELLED',        'Order was cancelled');

 INSERT INTO customer_address (
     customer_address_customer_id,
     customer_address_label,
     customer_address_city,
     customer_address_street,
     customer_address_building,
     customer_address_apartment,
     customer_address_phone_number,
     customer_address_note
 ) VALUES (
     '11111111-1111-1111-1111-111111111111',
     'Home',
     'Cairo',
     'Tahrir Street',
     '10',
     '3A',
     '+201234567890',
     'Ring the bell twice'
 );

------------------------------------------------------------------------------
-- Verification queries — run these after seeding to confirm
------------------------------------------------------------------------------
-- SELECT customer_id FROM customer
--  WHERE customer_id = '11111111-1111-1111-1111-111111111111';
--
-- SELECT menu_item_id, menu_item_name, menu_item_price
--   FROM menu_item
--  WHERE restaurant_menu_id = '55555555-5555-5555-5555-555555555555'
--  ORDER BY menu_item_name;
