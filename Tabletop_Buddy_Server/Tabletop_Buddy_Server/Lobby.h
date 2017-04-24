#ifndef Lobby_h
#define Lobby_h
#include "Character.h"
#include <vector>

class Lobby {
	private:
		//Lobby unique 6 digit ID, DM's lobby name, the ID of the Lobby owner, and lastly the vector containing all the pointers to each lobby
		std::string lobby_id;
		std::string lobby_name;
		int gm_id;
		std::vector<character> turn_order;
		
		//Custom sort method for turn_order, return true if second's initiative value is bigger than the first's initiative
		bool init_check(const Character& c1, const Character& c2) {return c1 < c2.}

	public:
		//lobby constructor: requires an ID, name given by the GM, and ID of the GM
		Lobby(std::string l_id, std::string l_name, int gm_id) {
			lobby_id = l_id;
			lobby_name = l_name;
			GM_ID = gm_id;
		}
		
		//adds a character to the vecotr
		void add_char(character cha) {
			turn_order.push_back(cha);
		}
		
		//reorder turn_order by intiative #
		void init_sort() {
			std::sort(turn_order.begin(), turn_order.end(), init_check());
		}
}
#endif