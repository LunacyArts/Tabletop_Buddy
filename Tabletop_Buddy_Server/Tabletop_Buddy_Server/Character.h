#ifndef Character_h
#define Character_h

class character {
	private:
		int initiative;
		std::string name;
	public:
		character(std::string n, int i) {
			initiative = i;
			name = n;
		}
		std::string getname() {
			return name;
		}
		int getInitiative() {
			return initiative;
		}
		void deleteChar() {
			character::~character();
		}
		~character() {}
	protected:
};

#endif