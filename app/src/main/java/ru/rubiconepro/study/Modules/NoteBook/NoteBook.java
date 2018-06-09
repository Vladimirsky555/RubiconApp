package ru.rubiconepro.study.Modules.NoteBook;

import java.util.ArrayList;
import java.util.List;

import ru.rubiconepro.study.Modules.Base.Base;
import ru.rubiconepro.study.Modules.NoteBook.Model.NoteWrapper;
import ru.rubiconepro.study.Modules.NoteBook.Model.NotesModel;
import ru.rubiconepro.study.Modules.NoteBook.Model.PartListModel;
import ru.rubiconepro.study.Modules.NoteBook.Model.PartModel;

public class NoteBook extends Base {
    //Создание синглтона
    public static final NoteBook instance = new NoteBook();
    private NoteBook() { }
    private PartListModel model = null;

    public PartListModel getModel() {
        //TODO Сделать Сбор данных из хранилища
        if (model == null) {
            model = new PartListModel();
        }
        return model;
    }

    public void addPart(String name) {
        model.items.add(new PartModel(name));
    }

    public void addNote(NotesModel nm, int positionPart, int positionNote) {
        if (positionNote == 0) {
            model.items.get(positionPart).listNotes.add(nm);
        } else {
            model.items.get(positionPart).listNotes.get(positionNote).notesList.add(nm);
        }
    }

    public PartModel getPartByPosition(int position) {
        if (position < 0 || position >= model.items.size())
            return null;

        return model.items.get(position);
    }

    private void AppendList(List<NoteWrapper> data, NotesModel model, NotesModel parent, int level) {

            NoteWrapper w = new NoteWrapper();
            w.model = model;
            w.parent = parent;
            w.level = level;

            data.add(w);

            if (model.notesList != null)
                for (NotesModel sub: model.notesList)
                    AppendList(data, sub, model, level + 1);
    }

    public List<NoteWrapper> getList (int position) {
        List<NoteWrapper> data = new ArrayList<>();

        PartModel m = this.getPartByPosition(position);

        for (NotesModel sub : m.listNotes)
            AppendList(data, sub, null, 0);

        return data;
    }

    public void deleteElement (int position, NoteWrapper element) {
        if (element.parent != null) {
            element.parent.notesList.remove(element.model);
        } else {
            getPartByPosition(position).listNotes.remove(element.model);
        }
    }

}
