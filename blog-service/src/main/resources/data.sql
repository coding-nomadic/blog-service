INSERT INTO public.tbl_user(
	id, email, full_name, is_enabled, mobile_number, password, token, user_name)
	VALUES (1, 'tnzngdw@gmail.com', 'Tenzin Dawa', false, '999999999', '$2a$12$EzqVNMXK90MdhWeVidIs8e8Jjh7VdA6c/oAJKO.IfD4yqfO3muefu','fcf28b07-3d5f-42f0-9393-2b05f780e20c', 'tendawa123');
INSERT INTO public.tbl_role(
	id, name)
	VALUES (1, 'ROLE_USER');	
INSERT INTO public.user_roles(
	user_id, role_id)
	VALUES (1, 1);


