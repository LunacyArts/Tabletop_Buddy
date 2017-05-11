#include "Character.h"
#include "Lobby.h"
#include "Server.h"

#include <iostream>
#include <thread>
#include <vector>
using namespace std;


int main(int argc, char *argv[]) {
	
	std::cout << "Starting Server\n";
	Server server = Server();
	system("pause");
	return 0;

}