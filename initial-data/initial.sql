INSERT INTO `admin_user` (`admin_user_id`, `created_date`, `email`, `full_name`, `is_notification_enable`, `is_password_change_required`, `login_name`, `mobile`, `password`, `profile_image_url`, `status`, `type_id`)
VALUES ('1', '2020-03-24 22:31:55', 'nishan@tt.com', 'Nishan Bro', '0', '0', 'nishan', '1231231232', 'C8FFE9A587B126F152ED3D89A146B445', '1', '2', '1');

INSERT INTO role_group_role(role_group_id, role_id) VALUE(1,0)

INSERT INTO admin_user_role_group(role_group_id, admin_user_id) VALUE(1,1)
