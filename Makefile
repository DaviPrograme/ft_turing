all:
	docker build -t turing .
	docker run --name 42-sp -it turing sh

clean: 
	docker system prune -a

fclean: clean

re: fclean all

.PHONE: all clean fclean re 