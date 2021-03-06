package ontology.effects.binary;

import java.util.Collection;

import core.vgdl.VGDLFactory;
import core.vgdl.VGDLSprite;
import core.content.InteractionContent;
import core.game.Game;
import core.logging.Logger;
import core.logging.Message;
import ontology.effects.Effect;
import tools.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/11/13
 * Time: 15:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class TeleportToExit extends Effect
{

    public TeleportToExit(InteractionContent cnt)
    {
        this.parseParameters(cnt);
    }

    @Override
    public void execute(VGDLSprite sprite1, VGDLSprite sprite2, Game game)
    {
	if(sprite1 == null || sprite2 == null){
            String[] className = this.getClass().getName().split("\\.");
            Logger.getInstance().addMessage(new Message(Message.WARNING, "[" + className[className.length - 1] + "] Either sprite1 or sprite2 is null."));
            return;
        }
	
        int destinationId = VGDLFactory.GetInstance().requestFieldValueInt(sprite2, "itype");
        if(destinationId == -1){
            String[] className = this.getClass().getName().split("\\.");
            Logger.getInstance().addMessage(new Message(Message.WARNING, "[" + className[className.length - 1] + "] " + sprite2 + " doesn't have itype."));
            return;
        }

        Collection<VGDLSprite> sprites = null;
        sprites = game.getSprites(destinationId);

        if(sprites.size() > 0){
            VGDLSprite destination = (VGDLSprite) Utils.choice(sprites.toArray(), game.getRandomGenerator());
            sprite1.setRect(destination.rect);
            sprite1.lastmove = 0;

            if(destination.is_oriented)
            {
                sprite1.orientation = destination.orientation.copy();
            }
        }else{
            //If there is no exit... kill the sprite
            //boolean variable set to false to indicate the sprite was not transformed
            game.killSprite(sprite1, false);
        }
    }
}
