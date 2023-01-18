# FATTO, RIVEDERE QUERY
import cmd
import sys
import random
import pandas as pd
import numpy as np
from neo4j import GraphDatabase
from Server.server import start_server
from Server.classifier import exportClassifier

class App(cmd.Cmd):
    intro = 'Sound classification server launched. \n \nType help or ? to list commands.\n'
    prompt = '>'
    neo4j_driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "root"))

    def do_retriveSounds(self, arg):
        'Download sounds'

    def do_exportClassifier(self, arg):
        'Export classifier model (needed for server startup)'
        exportClassifier()

    def do_startServer(self, arg):
        'Start server for classification'
        start_server()

    def do_initDB(self, arg):
        'Inizialize database'

        users_path = './data/users.json'
        sounds_path = './data/dataset.csv'

        # Initialization of utils
        session = self.neo4j_driver.session()
        users_df = pd.read_json(users_path, lines=True)
        sounds_df = pd.read_csv(sounds_path)
        # films_df.drop_duplicates('tmdb_id', keep="first", inplace=True)
        # films_df = films_df[~films_df.genre_ids.str.len().eq(0)]
        # films_df = films_df.replace(r'^\s*$', np.nan, regex=True)
        # films_df = films_df.dropna(axis=0, subset=['tmdb_id', 'title', 'overview', 'genre_ids', 'poster_path', 'original_language', 'production_companies', 'production_countries', 'release_date', 'casts'], how='any')
        # films_df = films_df[~(films_df.overview.str.len() <= 30)]
        # films_df = films_df.reset_index(drop=True)
        # dropGenres = ['Adventure', 'Mystery', 'Western', 'Science Fiction', 'Romance', 'Fantasy', 'Thriller', 'Family', 'War', 'History', 'Music', 'TV Movie']
        # films_df = films_df[~films_df['genre_ids'].isin(dropGenres)]
        # Drop old databases
        query = ("MATCH (n) DETACH DELETE n")
        session.write_transaction(lambda tx: tx.run(query))
        ### Neo4j database needs to be created

        ### Sounds
        for index, row in sounds_df.iterrows():
            query = (
                # REVIEW THE QUERY!!!!!!!!!!!
                "CREATE (f:Film { tmdb_id: $id, "
                "title: $title , "
                "plot: $overview , "
                "genre: $genre_ids, "
                "posterUrl: $poster_path, "
                "language: $language, "
                "production_companies: $production_companies, "
                "production_country: $production_country, "
                "release_date: $release_date, "
                "casts: $casts }) ")
            session.write_transaction(
                lambda tx: tx.run(query, id=str(row['tmdb_id']),
                                  title=row['title'],
                                  overview=row['overview'],
                                  genre_ids=row['genre_ids'],
                                  poster_path=row['poster_path'],
                                  language=row['original_language'],
                                  production_companies=row['production_companies'],
                                  production_country=row['production_countries'],
                                  release_date=row['release_date'],
                                  casts=row['casts'],
                                  type=False))

        print("Added sounds to database")

        ### Users
        for index, row in users_df.iterrows():
            query = ("CREATE (u:User { username: $username , "
                     "password: $password , "
                     "firstName: $firstName, "
                     "lastName: $lastName, "
                     "type: $type}) ")
            session.write_transaction(lambda tx: tx.run(query, username=row['username'],
                                                        password=row['password'],
                                                        firstName=row['firstName'],
                                                        lastName=row['lastName'],
                                                        type=False))

        print("Added users to database")

        ### Admin
        query = ("CREATE (u:User { username: $username , "
                 "password: $password , "
                 "firstName: $firstName, "
                 "lastName: $lastName, "
                 "type: $type}) ")
        session.write_transaction(lambda tx: tx.run(query, username='admin',
                                                    password='admin',
                                                    firstName='admin',
                                                    lastName='admin',
                                                    type=True))
        print("Added Administrator")

        # Users Likes
        # for index, row in users_df.iterrows():

        #    query = (
        #        "MATCH (a:User), (f:Film) "
        #        "WHERE a.username = $username AND (f.tmdb_id = $tmdb_id) "
        #        "CREATE (a)-[r:LIKES]->(f)"
        #    )

        #    n_likes = int(random.random() * 30)
        #    for i in range(0, n_likes):
        #        rand_film = films_df.sample()
        #        session.write_transaction(lambda tx: tx.run(query, username=row['username'],
        #                                                    tmdb_id=str(rand_film['tmdb_id'].values[0])))

        # print("Added likes to database")

    def do_exit(self, arg):
        'Exit Sound Classifier DB_Updater'
        self.neo4j_driver.close()
        sys.exit()

if __name__ == '__main__':
    App().cmdloop()