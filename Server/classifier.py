# FATTO
import numpy as np
import os
import librosa as librosa
# import sklearn
import pandas as pd
import joblib
# from sklearn.feature_extraction.text import TfidfVectorizer
from tqdm import tqdm
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import f_classif
# from sklearn.preprocessing import LabelEncoder
from sklearn.svm import SVC
# import re
# import nltk
# from nltk import word_tokenize
# from nltk.stem.snowball import SnowballStemmer

# Define stemming (not included by default in the scikit-learn codebase)
# class LemmaTokenizer:
#     def __init__(self):
#            self.stm = SnowballStemmer("english")
#     def __call__(self, doc):
#        return [self.stm.stem(t) for t in word_tokenize(doc)]

# encoder = LabelEncoder()

# def textCleaning(text):
#    text = re.sub("\'", "", text)
#    text = re.sub("[^a-zA-Z]", " ", text)
#    text = ' '.join(text.split())
#    text = text.lower()
#    return text


def loadDataset(path):
    # function for feature extraction
    def audio_feature_extraction(file):
        audio, sample_rate = librosa.load(file_name, res_type='kaiser_fast')
        mfcc_feat = librosa.feature.mfcc(y=audio, sr=sample_rate, n_mfcc=40)
        mfcc_scaled_features = np.mean(mfcc_feat.T, axis=0)
        return mfcc_scaled_features

    audio_dataset_path = '../JupyterNotebook/dataset/archive'
    metadata_path = pd.read_csv('../JupyterNotebook/dataset/archive/UrbanSound8K.csv')

    # iterate through each file and extract features
    extracted_features = []
    for index_num, row in tqdm(metadata_path.iterrows()):
        # print(row[1][-1])
        # break
        # ../input/urbansound8k/fold1/101415-3-0-2.wav
        # file_name = os.path.join(os.path.abspath(audio_dataset_path),'fold'+str(row[1][5])+'/',str(row[1][0]))
        # print(file_name)
        file_name = os.path.join(os.path.abspath(audio_dataset_path), 'fold' + str(row["fold"]) + '/',
                                 str(row['slice_file_name']))
        final_class_labels = row['class']  # row[1][-1]
        data = audio_feature_extraction(file_name)
        extracted_features.append([data, final_class_labels])

    reducedDataset = pd.DataFrame(extracted_features, columns=['feature', 'class'])

    # soundsDataset = pd.read_csv(path)
    # reducedDataset = soundsDataset[['feature', 'class']]

    return reducedDataset


# def removeStopWords(text):
#    #nltk.download('stopwords') #Download the first time
#    stopwords = nltk.corpus.stopwords.words('english')
#    stopwords += ['one','life', 'love', 'two', 'young', 'man', 'new', 'womand', 'get', 'family', 'film', 'also', 'set', 'become',
#                'comes', 'comes', 'best', 'even', 'movie', 'de', 'need', 'needs', 'needed', 'might', 'although', 'along', 'afterward', 'afterwards', 'already',
#                 'always', 'another', 'anothers', 'anytime', 'anything', 'anywhere', 'anyway', 'becoming', 'becomes', 'else',
#                'another', 'elsewhere', 'everyone', 'everything', 'every', 'many', 'meanwhile', 'moreover', 'nothing', 'nothings',
#                'nowhere', 'otherwise', 'perhaps', 'please', 'since', 'someone', 'something', 'sometime', 'sometimes', 'somewhere',
#                'thereby', 'therefore', 'whatever', 'whenever', 'whereabouts', 'whereby']
#    no_stopword_text = [w for w in text.split() if not w in stopwords]
#    return ' '.join(no_stopword_text)

# def low_stopwords(x):
#    return x.lower()


def exportClassifier():
    dataset = loadDataset("../JupyterNotebook/dataset/archive/extracted_features_df.csv")

    X = np.array(dataset['feature'].tolist())
    y = np.array(dataset['class'].tolist())
    # X_train_tfidf = tfidf_vectorizer.fit_transform(dataset['overview'])

    select_k = SelectKBest(f_classif, k=40)
    # selected_features = select_k.fit_transform(dataset['feature'], dataset['class'])
    selected_features = select_k.fit_transform(X, y)

    # clf = SVC(C=100, gamma=0.0001).fit(selected_features, dataset['class'])
    clf = SVC(C=100, gamma=0.0001).fit(selected_features, y)

    # joblib.dump(encoder, './classifier/encoder.pkl')
    # joblib.dump(tfidf_vectorizer, './classifier/tfidf_vectorizer.pkl')
    joblib.dump(select_k, './classifier/select_k.pkl')
    joblib.dump(clf, './classifier/svc.pkl')
    print("Models exported!")


if __name__ == "__main__":
    exportClassifier()