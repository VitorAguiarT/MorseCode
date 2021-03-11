INPUTS_DIR = ./TestCases/inputs
OUTPUTS_DIR = ./TestCases/outputs
VALIDATE_OUTPUTS_DIR = ./Validate/validation_outputs
SOLUTION = ./Solution/Solution.java
VALIDATE_JAVA = ./Validate/Main.java
VALIDATE_PYTHON = ./Validate/main.py

list: $(INPUTS_DIR)/*.in
	for file in $^ ; do \
	echo $$(basename $${file}) ; \
	done

run: $(INPUTS_DIR)/*.in
	for file in $^ ; do \
	java $(SOLUTION) < $${file} > $(OUTPUTS_DIR)/$$(basename $${file%.in}.out); \
	done

validate_java: $(INPUTS_DIR)/*.in
	for file in $^ ; do \
	echo $$(basename $${file%.in}):"\n" ; \
	echo "EXPECTED RESULT :" ; \
	java $(SOLUTION) < $${file} ; \
	echo "\nACTUAL RESULT :" ; \
	java $(VALIDATE_JAVA) < $${file}; \
	echo "\n-----------------------------\n" ; \
	done

validate_python: $(INPUTS_DIR)/*.in
	for file in $^ ; do \
	echo $$(basename $${file%.in}):"\n" ; \
	echo "EXPECTED RESULT :" ; \
	java $(SOLUTION) < $${file} ; \
	echo "\nACTUAL RESULT :" ; \
	python $(VALIDATE_PYTHON) < $${file}; \
	echo "\n-----------------------------\n" ; \
	done



validate_python3: $(INPUTS_DIR)/*.in
	for file in $^ ; do \
	python3 $(VALIDATE_PYTHON) < $${file} > $(VALIDATE_OUTPUTS_DIR)/$$(basename $${file%.in}.out); \
	done