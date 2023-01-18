# NON DOVREBBE SERVIRE
from tmdbv3api import TMDb
from tmdbv3api import Movie
from tmdbv3api.exceptions import TMDbException
import pandas as pd

tmdb = TMDb()
tmdb.api_key = '' #Need registretion on TMDb to get API key


def ListToFormattedString(alist):
    format_list = ['{:>3}' for item in alist]
    s = ','.join(format_list)
    return s.format(*alist)

def film_export():

    movie = Movie()
    for v in range(1, 100000):
        try:
            df = pd.DataFrame()
            n = movie.details(v)
            if len(n.genres) == 0:
                continue
            print(n.genres[0].name)
            print(v)
            casts = []
            for i in range(0, len(n.casts.cast)):
                casts.append(n.casts.cast[i].name)
            production_companies = []
            for i in range(0, len(n.production_companies)):
                production_companies.append(n.production_companies[i].name)

            contents = {'adult': n.adult,
                        'backdrop_path': n.backdrop_path,
                        'genre_ids': n.genres[0].name,
                        'tmdb_id': n.id,
                        'imdb_id': n.imdb_id,
                        'original_language': n.original_language,
                        'overview': n.overview,
                        'popularity': n.popularity,
                        'poster_path': n.poster_path,
                        'production_companies': ListToFormattedString(production_companies),
                        'production_countries': n.production_countries[0].name,
                        'release_date': n.release_date,
                        'title': n.title,
                        'vote_average': n.vote_average,
                        'vote_count': n.vote_count,
                        'casts': ListToFormattedString(casts),
                        }
            df = df.append(contents, ignore_index=True)
        except TMDbException:
            pass
        except IndexError:
            pass

    df.to_csv('./data/data_new.csv', float_format='%.0f', index=False)

if __name__ == "__main__":
    film_export()