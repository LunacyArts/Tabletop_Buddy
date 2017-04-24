#ifndef Server_h
#define Server_h
#include "Lobby.h"
#include "Character.h"
#include <vector>
#include <iostream>
#include <string>

class Server {
private:
	std::vector<Lobby> turn_order;
	const std::string digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int numChar = sizeof(digits) - 1;
public:
	//Server boot
	Server() {}

	//Lobby creation, requires a name and gm_id to create a lobby, generates a 6 digit pin using get_key(), then pushes it into vector turn_order
	std::string create_lobby(std::string l_name, int gm_id) {
		//generate 6 digit key
		std::string key = Server::get_key();
		//cout to server cmd window
		std::cout << "Starting new server with key: " << key;
		//creates lobby and adds it to the turn_oder vector
		turn_order.push_back(Lobby(key, l_name, gm_id));
		//cout to server cmd window completion
		std::cout << "Lobby Startedwith key: " << key << " and name of " << l_name << " for GM: " << gm_id;
	}
	void close_lobby(std::string id) {

	}
	std::string get_key() {
		std::string id = "";
		for (int i = 0; i < 6; i++) {
			id + digits[rand() % numChar];
		}
		return id;
	}
}
#endif