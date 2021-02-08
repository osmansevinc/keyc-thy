package com.example.demo.service.impl;

import java.util.Arrays;
import java.util.Collections;

import javax.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.example.demo.data.UserCredential;
import com.example.demo.data.UserRegisterDTO;
import com.example.demo.service.service.IKeycloakAPIService;

@Service
public class KeycloakAPIServiceImpl implements IKeycloakAPIService {

	private static final Logger logger = LoggerFactory.getLogger(KeycloakAPIServiceImpl.class);

	@Autowired
	private Environment env;

	public String getAccessToken(UserCredential user) throws Exception {
		KeycloakBuilder keycloakBuilder = this.newKeycloakBuilderWithPasswordCredentials(user.getEmail(),
				user.getPassword());
		return keycloakBuilder.build().tokenManager().getAccessToken().getToken();
	}

	private KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password)
			throws Exception {
		try {
			return newKeycloakBuilderWithClientCredentials() //
					.username(username) //
					.password(password) //
					.grantType(OAuth2Constants.PASSWORD);
		} catch (Exception e) {
			logger.error("[KeycloakAPIServiceImpl][newKeycloakBuilderWithPasswordCredentials][error] : {} ", e);
			throw new Exception();
		}
	}

	private KeycloakBuilder newKeycloakBuilderWithClientCredentials() throws Exception {
		try {
			return KeycloakBuilder.builder() //
					.realm(env.getProperty("keycloak.realm")) //
					.serverUrl(env.getProperty("keycloak.auth-server-url"))//
					.clientId(env.getProperty("keycloak.resource")) //
					.clientSecret(env.getProperty("keycloak.credentials.secret")) //
					.grantType(OAuth2Constants.CLIENT_CREDENTIALS);
		} catch (Exception e) {
			logger.error("[KeycloakAPIServiceImpl][newKeycloakBuilderWithClientCredentials][error] : : {} ", e);
			throw new Exception();
		}
	}

	/*
	 * @Override public boolean registerNewUserAccount(UserRegisterDTO user) throws
	 * Exception { boolean result = false; try { String keycloakPassword =
	 * user.getPassword();
	 * 
	 * Keycloak keycloak =
	 * Keycloak.getInstance(env.getProperty("keycloak.auth-server-url"),
	 * env.getProperty("keycloak.realm"), // your realm
	 * env.getProperty("keycloak-user.user"), // user
	 * env.getProperty("keycloak-user.pass"), // password
	 * env.getProperty("keycloak.resource")); // client
	 * 
	 * CredentialRepresentation credential = new CredentialRepresentation();
	 * credential.setType(CredentialRepresentation.PASSWORD);
	 * credential.setValue(keycloakPassword);
	 * 
	 * UserRepresentation keycloakUser = new UserRepresentation();
	 * keycloakUser.setUsername(user.getUsername());
	 * keycloakUser.setFirstName(user.getFirstName());
	 * keycloakUser.setLastName(user.getLastName());
	 * keycloakUser.setEmail(user.getEmail());
	 * keycloakUser.setCredentials(Arrays.asList(credential));
	 * keycloakUser.setEnabled(true);
	 * keycloakUser.setRealmRoles(Arrays.asList("user"));
	 * 
	 * // Get realm RealmResource realmResource =
	 * keycloak.realm(env.getProperty("keycloak.realm")); UsersResource
	 * usersRessource = realmResource.users();
	 * 
	 * // Create Keycloak user Response response =
	 * usersRessource.create(keycloakUser);
	 * 
	 * if (response == null || response.getStatus() != 201) result = false; else
	 * result = true;
	 * 
	 * } catch (Exception e) { logger.
	 * error("[KeycloakAPIServiceImpl][registerNewUserAccount][error] : : {} ", e);
	 * throw new Exception(); } return result; }
	 */

	@Override
	public int registerNewUserAccount(UserRegisterDTO userDTO) throws Exception {
		Response response = null;
		try {
//			// Client "idm-client" needs service-account with at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
			Keycloak keycloak = KeycloakBuilder.builder() //
					.serverUrl(env.getProperty("keycloak.auth-server-url")) //
					.realm(env.getProperty("keycloak.realm")) //
					.grantType(OAuth2Constants.CLIENT_CREDENTIALS) //
					.clientId(env.getProperty("keycloak.resource")) //
					.clientSecret(env.getProperty("keycloak.credentials.secret")).build();

			// User "idm-admin" needs at least "manage-users, view-clients, view-realm,
			// view-users" roles for "realm-management"
			/*
			 * Keycloak keycloak = KeycloakBuilder.builder() //
			 * .serverUrl(env.getProperty("keycloak.auth-server-url")) //
			 * .realm(env.getProperty("keycloak.realm")) //
			 * .grantType(OAuth2Constants.PASSWORD) //
			 * .clientId(env.getProperty("keycloak.resource")) //
			 * .clientSecret(env.getProperty("keycloak.credentials.secret")) //
			 * .username(env.getProperty("keycloak-user.user")) //
			 * .password(env.getProperty("keycloak-user.pass"))
			 * .authorization("authorization")// .resteasyClient(new
			 * ResteasyClientBuilder().connectionPoolSize(10).register(new
			 * CustomJacksonProvider()).build()) .build();
			 */

			// Define user
			UserRepresentation user = new UserRepresentation();
			user.setEnabled(true);
			user.setUsername(userDTO.getUsername());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

			// Get realm
			RealmResource realmResource = keycloak.realm(env.getProperty("keycloak.realm"));
			UsersResource usersRessource = realmResource.users();

			// Create user (requires manage-users role)
			response = usersRessource.create(user);
			System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
			if (response != null && response.getStatus() != 201)
				return response.getStatus();

			String userId = CreatedResponseUtil.getCreatedId(response);

			System.out.printf("User created with userId: %s%n", userId);

			// Define password credential
			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(userDTO.getPassword());

			UserResource userResource = usersRessource.get(userId);

			// Set password credential
			userResource.resetPassword(passwordCred);

		} catch (Exception e) {
			logger.error("[KeycloakAPIServiceImpl][registerNewUserAccount][error] : : {} ", e);
			throw new Exception();
		}

		return response.getStatus();

	}

}
